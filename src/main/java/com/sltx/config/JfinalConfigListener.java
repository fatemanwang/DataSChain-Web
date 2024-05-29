package com.sltx.config;

import com.sltx.interceptor.CrossInterceptor;
import io.jboot.aop.jfinal.JfinalHandlers;
import io.jboot.aop.jfinal.JfinalPlugins;
import io.jboot.server.listener.JbootAppListenerBase;

import com.alibaba.druid.support.jconsole.DruidPlugin;
import com.jfinal.config.Constants;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.sltx.interceptor.AuthInterceptor;
import com.sltx.interceptor.LogInterceptor;

public class JfinalConfigListener extends JbootAppListenerBase {
	
	@Override
    public void onJfinalConstantConfig(Constants constants) {
        
    }
	
	@Override
	public void onJfinalPluginConfig(JfinalPlugins plugins) {
			//plugins.add(new DruidPlugin());
	}
	 
	@Override
    public void onJfinalRouteConfig(Routes routes) {
        routes.setBaseViewPath("/html");
    }
	
	@Override
    public void onHandlerConfig(JfinalHandlers handlers) {
        handlers.add(new ContextPathHandler("ctx"));
        handlers.add(1,new DruidStatViewHandler("/druid"));
        
    }
	@Override
    public void onInterceptorConfig(Interceptors interceptors) {
        interceptors.add(new LogInterceptor());
        interceptors.add(new AuthInterceptor());
      /*  interceptors.add(new NotNullParaInterceptor("/template/exception.html"));
        interceptors.add(new BusinessExceptionInterceptor("/template/exception.html"));*/
    }

}
