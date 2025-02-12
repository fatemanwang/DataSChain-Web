package com.sltx.shiro.auth;

import io.jboot.Jboot;

import com.sltx.entity.model.Res;
import com.sltx.entity.model.Role;
import com.sltx.entity.model.User;
import com.sltx.entity.status.UserStatus;
import com.sltx.service.api.ResService;
import com.sltx.service.api.RoleService;
import com.sltx.service.api.UserService;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

/**
 * management Side Authentication And Authorization
 * @author Rlax
 *
 */
public class LoginAuth implements MuitiAuthenticatied {

    @Override
    public boolean hasToken(AuthenticationToken authenticationToken) {
        String loginName = authenticationToken.getPrincipal().toString();
        UserService sysUserApi = Jboot.bean(UserService.class);
        return sysUserApi.hasUser(loginName);
    }

    @Override
    public boolean wasLocked(AuthenticationToken authenticationToken) {
        String loginName = authenticationToken.getPrincipal().toString();

        UserService sysUserApi = Jboot.bean(UserService.class);
        User sysUser = sysUserApi.findByName(loginName);
        return !sysUser.getStatus().equals(UserStatus.USED);
    }

    @Override
    public AuthenticationInfo buildAuthenticationInfo(AuthenticationToken authenticationToken) {
        String loginName = authenticationToken.getPrincipal().toString();

        UserService sysUserApi = Jboot.bean(UserService.class);
        User sysUser = sysUserApi.findByName(loginName);
        String salt2 = sysUser.getSalt2();
        String pwd = sysUser.getPwd();

        return new SimpleAuthenticationInfo(loginName, pwd, ByteSource.Util.bytes(salt2), "ShiroDbRealm");
    }

    @Override
    public AuthorizationInfo buildAuthorizationInfo(PrincipalCollection principals) {
        String loginName = (String) principals.fromRealm("ShiroDbRealm").iterator().next();

        RoleService sysRoleApi = Jboot.bean(RoleService.class);
        List<Role> sysRoleList = sysRoleApi.findByUserName(loginName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<String> roleNameList = new ArrayList<String>();
        for (Role sysRole : sysRoleList) {
            roleNameList.add(sysRole.getName());
        }

        ResService sysResService = Jboot.bean(ResService.class);
        List<Res> sysResList = sysResService.findByUserNameAndStatusUsed(loginName);
        List<String> urls = new ArrayList<String>();
        for (Res sysRes : sysResList) {
            System.out.println(sysRes.getDes());
            urls.add(sysRes.getUrl());
        }

        info.addRoles(roleNameList);
        info.addStringPermissions(urls);
        return info;
    }
}
