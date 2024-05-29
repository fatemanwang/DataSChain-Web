package com.sltx.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;


public class MuitiLoginToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 13810646381777514L;


	public final static String USERPASSWORD_MODE = "userpassword_mode";

	public final static String TOKEN_MODE = "token_mode";
	

	private String loginType = USERPASSWORD_MODE;

	public String getLoginType() {
		return this.loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}


	public MuitiLoginToken(String username, String password) {
		super(username, password);
	}

}
