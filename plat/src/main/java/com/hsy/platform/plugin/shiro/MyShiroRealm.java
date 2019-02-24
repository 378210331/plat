package com.hsy.platform.plugin.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;


/**
 * 自定义shiro领域
 */

/**
 * 授权
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        //从数据库或者缓存获取roles set
        Set<String> roles  = new HashSet<String>();
        //从数据库或者缓存获取permision set
        Set<String> permision  = new HashSet<String>();
        return null;
    }


    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username  = (String)token.getPrincipal();
        //查询数据库，获取密码
        String password = "password";
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,"MyShiroRealm");
        return info;
    }
}
