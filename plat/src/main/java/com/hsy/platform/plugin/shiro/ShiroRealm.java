package com.hsy.platform.plugin.shiro;



import com.hsy.platform.service.UserService;
import com.hsy.platform.plugin.PageData;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;


/**
 * @author fh
 *  2015-3-6
 */
public class ShiroRealm extends AuthorizingRealm {
    @Resource(name="UserService")
    private UserService userService;
    /*
     * 登录信息和用户验证信息验证(non-Javadoc)
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        PageData pd=null;
        try {
            pd=userService.getUserByLoginName(token.getPrincipal().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pd!=null){
            return new SimpleAuthenticationInfo(pd.getString("USERNAME"), pd.getString("PASSWORD"), getName());
        }else{
            return null;
        }

    }

    /*
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
     * 决定角色数据，可通过配置spring-shiro的访问规则进行过滤
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        return null;
    }

}


