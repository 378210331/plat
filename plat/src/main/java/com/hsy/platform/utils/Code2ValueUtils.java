package com.hsy.platform.utils;


import com.hsy.platform.common.Const;
import com.hsy.platform.plugin.Cache.PlatCacheManager;
import com.hsy.platform.plugin.PageData;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代码转名称-工具类
 * 转换依赖 t_sys_cache
 * 当system.properties的platCacheManager 不为true的时候失效
 */
public class Code2ValueUtils {

    static PlatCacheManager manager = (PlatCacheManager) Const.WEB_APP_CONTEXT.getBean("platCacheManager");

    /**
     * 记录重载时间
     */
    static Map<String,Long> reloadTimeMap = new ConcurrentHashMap<String,Long>();

    /**
     * 代码转名称
     * @param key
     * @param pageData  传入的待转换pd
     */
    public static void code2Value(String key,String column, PageData pageData) throws Exception {
        String targetCol = column + "$text";
    	if(StringUtils.isNotBlank(key) && "true".equalsIgnoreCase(PropertiesUtils.getValueByKey("platCacheManager"))) {
            List<Map<String,Object>> pageDataList = manager.getCacheDataByKey(key);
            String keyValue=(String) pageData.get(column);
            if(StringUtils.isNotBlank(keyValue)) {
                for (Map<String, Object> map : pageDataList) {
                    if(keyValue.equals(map.get("code"))){
                        pageData.addParam(targetCol,map.get("value"));
                        return ;
                    }
                }
            }
            //未命中，一小时内自动重载
            if(reloadTimeMap.get(key) ==null || System.currentTimeMillis() - reloadTimeMap.get(key) >3600 * 1000){
                manager.cacheReload(key);
                reloadTimeMap.put(key,System.currentTimeMillis());
                code2Value(key,column,pageData);
                return ;
            }
    	}
    	//加载后仍未命中或者未开启系统缓存，则直接返回
        pageData.addParam(targetCol,pageData.get(column));
    }

    /**
     * 代码转名称
     * @param key
     * @param pageDataList
     */
    public static void code2Value(String key,String column,List<PageData> pageDataList) throws Exception {
    	if(StringUtils.isNotBlank(key)) {
    		for(PageData pd : pageDataList){
    			code2Value(key,column,pd);
    		}
    	}
    }


}
