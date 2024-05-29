package com.sltx.shiro.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * multipurpose Authenticator
 * @author Rlax
 *
 */
public interface MuitiAuthenticatied {

    public boolean hasToken(AuthenticationToken authenticationToken);

    public boolean wasLocked(AuthenticationToken authenticationToken);

    public AuthenticationInfo buildAuthenticationInfo(AuthenticationToken authenticationToken);


    public AuthorizationInfo buildAuthorizationInfo(PrincipalCollection principals);
}
