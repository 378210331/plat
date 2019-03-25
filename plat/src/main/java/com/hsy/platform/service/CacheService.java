package com.hsy.platform.service;


import com.hsy.platform.dao.JdbcDao;
import com.hsy.platform.plugin.SimpleRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("CacheService")
public class CacheService{

    @Resource
    JdbcDao jdbcDao;

    public List<Map<String,Object>> getCacheInfoList(String key) throws Exception {
           String sql = "select * from t_sys_cache" ;
           if(StringUtils.isNotBlank(key)){
               sql  += " where  key = '"+key+"' ";
           }
          return  jdbcDao.getJdbcTemplate().query(sql,new SimpleRowMapper());
    }

    public Map<String,Object> getCacheData(List<Map<String,Object>> pageDataList){
        Map<String,Object> result = new ConcurrentHashMap<>();
        StringBuffer sb = new StringBuffer();
        for(Map<String,Object> m: pageDataList){
            String sql = m.get("sql").toString();
            String key = m.get("key").toString();
            sql = jdbcDao.getPaginationSql(sql,1000,1);
            try {
                List<Map<String, Object>> cacheList = jdbcDao.getJdbcTemplate().query(sql, new SimpleRowMapper());
                result.put(key, cacheList);
            }catch (Exception e){
                sb.append("unable to load cache from database  which key named : ").append(key);
            }
        }
        result.put("msg",sb.toString());
        return result;
    }
}
