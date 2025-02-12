package com.sltx.shiro;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionHandler extends Handler
{
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled)
	{
		int index = target.indexOf(";jsessionid".toUpperCase());
		if (index != -1) {
			target = target.substring(0, index);
		}
		next.handle(target, request, response, isHandled);
	}
}
