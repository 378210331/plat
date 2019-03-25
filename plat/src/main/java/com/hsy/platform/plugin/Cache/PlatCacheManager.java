package com.hsy.platform.plugin.Cache;

import com.hsy.platform.service.CacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.ehcache.EhCacheManagerUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PlatCacheManager implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(getClass());


    CacheService cacheService;

    String EHCACHE_CACHE_TYPE = "EhCache";
    String REDIS_CACHE_TYPE = "redis";

    String DEFAULT_CACHE_TYPE = "EhCache";


    String cacheType;

    CacheManager cacheManager;

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public List<Map<String,Object>> getCacheDataByKey(String key){
        Cache cache = cacheManager.getCache("systemCache");
        Serializable object  = cache.get(key);
        if(object != null){
            return (List<Map<String,Object>>)((Element) object).getValue();
        }else {
            return new ArrayList<Map<String,Object>>();
        }
    }


    public void cacheReload(String key) throws Exception {
        List<Map<String,Object>> cacheList = null;
        Cache cache = cacheManager.getCache("systemCache");
        cache.remove(key);
        System.out.println("/**********loading system cache key:"+key+"*****************/");
        cacheList  = cacheService.getCacheInfoList(key);
        Map<String,Object> cacheMap = cacheService.getCacheData(cacheList);
        if(EHCACHE_CACHE_TYPE.equals(getCacheType())){
            for(Map.Entry entry : cacheMap.entrySet()){
                if(cache ==null){
                    logger.error("unable to getCache 'systemCache',please check ehcache config");
                    System.out.println("/********** finish loading system cache*****************/");
                    return ;
                }
                Element element = new Element(entry.getKey(),entry.getValue());
                cache.put(element);
            }
        }else{
            //TODO  REDIS
        }
        System.out.println("/********** success loading system cache key::"+key+"*****************/");
        System.out.println("/********** finish loading system cache*****************/");
    }



    public void cacheReloadAll(){
        Cache cache = cacheManager.getCache("systemCache");
        cache.removeAll();
        cacheInit();
    }

    public void cacheInit() {
        List<Map<String,Object>> cacheList = null;
        System.out.println("/**********loading system cache*****************/");
        try{
             cacheList  = cacheService.getCacheInfoList(null);
        }catch (Exception e){
            logger.error("unable to load cache info from database,please check table t_sys_cache");
            return ;
        }
        Map<String,Object> cacheMap = cacheService.getCacheData(cacheList);
        String msg  = cacheMap.get("msg").toString();
        if(StringUtils.isNotBlank(msg)){
            logger.warn(msg);
        }
        cacheMap.remove("msg");
        int success_num = 0;
        if(EHCACHE_CACHE_TYPE.equals(getCacheType())){
                for(Map.Entry entry : cacheMap.entrySet()){
                    Cache cache = cacheManager.getCache("systemCache");
                    if(cache ==null){
                        logger.error("unable to getCache 'systemCache',please check ehcache config");
                        System.out.println("/********** finish loading system cache*****************/");
                        return ;
                    }
                    Element element = new Element(entry.getKey(),entry.getValue());
                    cache.put(element);
                    success_num ++;
                }
        }else{
                //TODO  REDIS
        }
        System.out.println("/********** success loading system cache :"+success_num+"*****************/");
        System.out.println("/********** finish loading system cache*****************/");
    }

    @Override
    public void afterPropertiesSet(){
        if (this.getCacheType() == null) {
            setCacheType(DEFAULT_CACHE_TYPE);
                this.setCacheManager(EhCacheManagerUtils.buildCacheManager());
        }
    }

    public CacheService getCacheService() {
        return cacheService;
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }
}
