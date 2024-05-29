package com.sltx.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.sltx.common.model.RestResult;

import io.jboot.exception.JbootException;
import io.jboot.web.controller.JbootController;

/**
 * Business exception interceptor
 * @author Rlax
 *
 */
public class BusinessExceptionInterceptor implements Interceptor {


	public final static String MESSAGE_TAG = "message";


	private String exceptionView = "/exception.html";

	public BusinessExceptionInterceptor(String exceptionView) {
		this.exceptionView = exceptionView;
	}

	@Override
	public void intercept(Invocation inv) {
		try {
			inv.invoke();
		} catch (JbootException e) {
			if (inv.getTarget() instanceof JbootController) {
				JbootController controller = inv.getTarget();

				if (controller.isAjaxRequest()) {
					RestResult<String> restResult = new RestResult<String>();
					restResult.error(e.getMessage());
					controller.renderJson(restResult);
				} else {
					controller.setAttr(MESSAGE_TAG, e.getMessage()).render(exceptionView);
				}
			}
		}
	}

}
