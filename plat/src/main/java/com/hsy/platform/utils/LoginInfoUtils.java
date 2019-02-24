package com.hsy.platform.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.util.List;

/**
 * 权限控制util
 */
public class LoginInfoUtils {

    public static String  getUserName(){
        return getSession().getAttribute("userName").toString();
    }

    public static String  getSwjgDm(){
        Object o = getSession().getAttribute("deptDm");
        return o.toString();
    }

    public static String  getUserId(){
        return getSession().getAttribute("userId").toString();
    }

    public static Session getSession(){
       return SecurityUtils.getSubject().getSession();
    }


}
