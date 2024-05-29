package com.sltx.shiro;

import com.sltx.shiro.auth.MuitiAuthenticatied;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * ShiroDbRealm
 * @author Rlax
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {


	private MuitiAuthenticatied muitiAuthenticatied;

	@Override
	public void setCacheManager(CacheManager cacheManager) {
		super.setCacheManager(cacheManager);
		ShiroCacheUtils.setCacheManager(cacheManager);
	}


	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		if (!muitiAuthenticatied.hasToken(authcToken)) {

			throw new UnknownAccountException();
		}

		if (muitiAuthenticatied.wasLocked(authcToken)) {

			throw new LockedAccountException();
		}

		return muitiAuthenticatied.buildAuthenticationInfo(authcToken);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return muitiAuthenticatied.buildAuthorizationInfo(principals);
	}


	public MuitiAuthenticatied getMuitiAuthenticatied() {
		return muitiAuthenticatied;
	}

	public void setMuitiAuthenticatied(MuitiAuthenticatied muitiAuthenticatied) {
		this.muitiAuthenticatied = muitiAuthenticatied;
	}
}
