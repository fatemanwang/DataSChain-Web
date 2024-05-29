package com.sltx.common.consts;

/**
 * cache directory key
 * @author Rlax
 *
 */
public class CacheKey {

	/** The basic data corresponds to the name of the data table keyValue cached in the cache */
	public static final String CACHE_KEYVALUE = "keyValue";

	/** page data cache */
	public static final String CACHE_PAGE = "pageCache";

	/** 30 minutes cache */
	public static final String CACHE_H1M30 = "h1m30";
	
	/** menuCache */
	public static final String CACHE_MENU = "menuCache";
	
	/** shiro authorizationCach */
	public static final String CACHE_SHIRO_AUTH = "shiro-authorizationCache";
	
	/** shiro session online cache */
	public static final String CACHE_SHIRO_ACTIVESESSION = "shiro-active-session";

	/** shiro session kick cache */
	public static final String CACHE_SHIRO_KICKOUTSESSION = "shiro-kickout-session";
	
	/** shiro Password Retry Cache */
	public static final String CACHE_SHIRO_PASSWORDRETRY = "shiro-passwordRetryCache";
	
	/** shiro SESSION KEY cache*/
	public static final String CACHE_SHIRO_SESSION = "shiro-session";
	
	/** verification code cache */
	public static final String CACHE_CAPTCHAR_SESSION = "captchar-cache";

	/** jwt_token */
	public static final String CACHE_JWT_TOKEN = "jwt_token";
}
