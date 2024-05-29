package com.sltx.shiro;

import com.sltx.common.consts.CacheKey;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.io.Serializable;

/**
 * ShiroCache tools
 * @author Rlax
 *
 */
public class ShiroCacheUtils
{
	private static CacheManager cacheManager;

	public static void clearAuthorizationInfo(String username)
	{
		Cache<Object, Object> cache = cacheManager.getCache(CacheKey.CACHE_SHIRO_AUTH);
		cache.remove(username);
	}

	public static void clearAuthorizationInfoAll()
	{
		Cache<Object, Object> cache = cacheManager.getCache(CacheKey.CACHE_SHIRO_AUTH);
		cache.clear();
	}

	public static void clearSession(Serializable sessionId)
	{
		Cache<Object, Object> cache = cacheManager.getCache(CacheKey.CACHE_SHIRO_ACTIVESESSION);
		cache.remove(sessionId);
	}

	public static CacheManager getCacheManager()
	{
		return cacheManager;
	}

	public static void setCacheManager(CacheManager cacheManager)
	{
		ShiroCacheUtils.cacheManager = cacheManager;
	}

}
