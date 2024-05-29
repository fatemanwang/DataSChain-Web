package com.sltx.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.sltx.common.Consts;
import com.sltx.common.model.RestResult;
import com.sltx.entity.model.User;
import com.sltx.service.api.UserService;
import com.sltx.shiro.MuitiLoginToken;
import com.sltx.validator.LoginValidator;

import io.jboot.core.rpc.annotation.JbootrpcService;
import io.jboot.web.controller.JbootController;
import io.jboot.web.controller.annotation.RequestMapping;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

/**
 * main controller
 * @author Rlax
 *
 */
@RequestMapping("/")
public class MainController extends JbootController {

	//@JbootrpcService
		@Inject
    private UserService userService;

    public void index() {
        render("login.html");
    }

    public void login() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
//            redirect("/");
            render("index.html");
        } else {
            render("login.html");
        }
    }

    public void captcha() {
        renderCaptcha();
    }

    @Before( {POST.class, LoginValidator.class} )
    public void postLogin() {
        String loginName = getPara("loginName");
        String pwd = getPara("password");
        MuitiLoginToken token = new MuitiLoginToken(loginName, pwd);
        Subject subject = SecurityUtils.getSubject();
        RestResult<String> restResult = new RestResult<String>();
        restResult.success().setMsg("login successful");

        try {
            if (!subject.isAuthenticated()) {
                token.setRememberMe(false);
                subject.login(token);

                User u = userService.findByName(loginName);
                subject.getSession(true).setAttribute(Consts.SESSION_USER, u);
            }

        } catch (UnknownAccountException une) {
            restResult.error("Username does not exist");
        } catch (LockedAccountException lae) {
            restResult.error("user locked");
        } catch (IncorrectCredentialsException ine) {
            restResult.error("Incorrect username or password");
        } catch (ExcessiveAttemptsException exe) {
            restResult.error("There are too many wrong account passwords, and the account has been restricted from logging in for 1 hour");
        } catch (Exception e) {
            e.printStackTrace();
            restResult.error("Service exception, please try again later");
        }

        renderJson(restResult);
    }

    /**
     * Quit
     */
    public void logout() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            SecurityUtils.getSubject().logout();
        }
        render("login.html");
    }

    public void welcome() {
        render("welcome.html");
    }

}
