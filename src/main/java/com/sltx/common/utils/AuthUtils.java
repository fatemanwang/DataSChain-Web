package com.sltx.common.utils;

import com.sltx.common.Consts;
import com.sltx.entity.model.User;
import com.sltx.shiro.ShiroUtils;

import org.apache.shiro.SecurityUtils;

/**
 * Authorization and authentication tools
 */
public class AuthUtils {


    public static boolean isLogin() {
        return ShiroUtils.isAuthenticated();
    }

    /**
     * Get platform login user
     * @return
     */
    public static User getLoginUser() {
        User user = new User();
        if (ShiroUtils.isAuthenticated()) {
            user = (User) SecurityUtils.getSubject().getSession().getAttribute(Consts.SESSION_USER);
        }
        return user;
    }

    /**
     * Verify user login password
     * @param newPwd
     * @param oldPwd
     * @param oldSalt2
     * @return true or false
     */
    public static boolean checkPwd(String newPwd, String oldPwd, String oldSalt2) {
        return ShiroUtils.checkPwd(newPwd, oldPwd, oldSalt2);
    }
}
