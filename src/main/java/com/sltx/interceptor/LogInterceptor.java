package com.sltx.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import io.jboot.Jboot;

import com.sltx.common.base.BaseController;
import com.sltx.common.utils.AuthUtils;
import com.sltx.entity.model.Log;
import com.sltx.entity.model.User;
import com.sltx.service.api.LogService;

import java.util.Date;

/**
 * Syslog Interceptor
 * @author Rlax
 *
 */
public class LogInterceptor implements Interceptor {

	private final static com.jfinal.log.Log logger = com.jfinal.log.Log.getLog(LogInterceptor.class);

	@Override
	public void intercept(Invocation inv) {
		if (inv.getController() instanceof BaseController) {
			BaseController c = (BaseController) inv.getController();

			User user = AuthUtils.getLoginUser();
			UserAgent userAgent = UserAgent.parseUserAgentString(c.getRequest().getHeader("User-Agent"));
			Browser browser = userAgent.getBrowser();

			Log log = new Log();
			log.setUid(user.getId());
			log.setBrowser(browser.getName());
			log.setOperation(c.getRequest().getMethod());
			log.setFrom(c.getReferer());
			log.setIp(c.getIPAddress());
			log.setUrl(c.getRequest().getRequestURI());
			log.setCreateDate(new Date());
			log.setLastUpdAcct(user.getId() == null ? "guest" : user.getName());
			log.setLastUpdTime(new Date());
			log.setNote("记录日志");

			try {
				LogService logService = Jboot.bean(LogService.class);
				logService.save(log);
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} finally {
				inv.invoke();
			}
		} else {
			inv.invoke();
		}
	}

}
