package com.sltx.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import io.jboot.Jboot;

import com.sltx.entity.model.Res;
import com.sltx.entity.status.ResStatus;
import com.sltx.service.api.ResService;

import org.apache.shiro.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * According to the url permission interceptor,
 * only roles with url permissions are allowed to access
 * @author Rlax
 *
 */
public class AuthInterceptor implements Interceptor {


    private static List<String> urls;

    public AuthInterceptor() {

    }

    public static List<String> getUrls() {
        return urls;
    }

    public static void init() {
        ResService sysResApi = Jboot.bean(ResService.class);
        List<Res> sysResList = sysResApi.findByStatus(ResStatus.USED);
        List<String> list = new ArrayList<String>();
        for (Res res : sysResList) {
            list.add(res.getUrl());
        }
        urls = list;
    }

    @Override
    public void intercept(Invocation ai) {
        if (urls == null) {
            init();
        }

        String url = ai.getActionKey();
        boolean flag = SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(url);

        if (urls.contains(url) && !flag) {
            ai.getController().renderError(403);
        } else {
            ai.invoke();
        }
    }

}
