package com.hsy.platform.plugin.Cache;

import com.alibaba.fastjson.JSON;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class CacheKeyGenerator implements KeyGenerator {

    private final static int NO_PARAM_KEY = 0;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuffer sb = new StringBuffer();
        char sp = ':';
        sb.append(target.getClass().getSimpleName());
        sb.append(sp);
        sb.append(method.getName());
        sb.append(sp);
        if (params.length > 0) {
            // 参数值
            for (Object object : params) {
                if(object == null )continue;
                if (isSimpleValueType(object.getClass())) {
                    sb.append(object);
                } else {
                    sb.append(JSON.toJSONString(object).hashCode());
                }
            }
        } else {
            sb.append(NO_PARAM_KEY);
        }
        return sb.toString();
    }

    public static boolean isSimpleValueType(Class<?> clazz) {
        return (ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isEnum() || CharSequence.class.isAssignableFrom(clazz)
                || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class == clazz
                || URL.class == clazz || Locale.class == clazz || Class.class == clazz);
    }
}

