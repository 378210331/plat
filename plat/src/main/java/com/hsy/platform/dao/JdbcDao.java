package com.hsy.platform.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class JdbcDao   implements ApplicationContextAware {

    public Integer queryForInt(String sql ,Object... args){
        Number number = (Number)this.getJdbcTemplate().queryForObject(sql, args, Integer.class);
        return number != null ? number.intValue() : 0;
    }

    public int queryForInt(String sql, Map<String, ?> paramMap) throws DataAccessException {
        Number number =  getNamedParameterJdbcTemplate().queryForObject(sql,paramMap,Integer.class);
        return number != null ? number.intValue() : 0;
    }


    @Autowired
    @Qualifier("jdbcTemplate")
    public JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public String getPaginationSql(String sql, int pageSize, int pageIndex) {
        if(pageIndex>0 && pageSize >0){
            int startNo = (pageIndex - 1) * pageSize;

          /*  String paginationSql = "select * from (select A.*,ROWNUM RN from ("
                    + sql + ") A WHERE ROWNUM <= " + (startNo + pageSize)
                    + ") WHERE RN > " + startNo;*/
          String paginationSql = "select * from ("+sql + ") limit "+startNo+","+pageSize;
            return paginationSql;
        }else{
            return sql;
        }
    }

    public String getCountSql(String sql){

        return "select count(1) from (" + sql + ")";
    }


    protected ApplicationContext applicationContext;

    protected static Logger log = LoggerFactory.getLogger(JdbcDao.class);

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return  namedParameterJdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(String dataSourceName) {
        if(dataSourceName == null || "".equalsIgnoreCase(dataSourceName)){
            return namedParameterJdbcTemplate;
        }else{
            Object o =  applicationContext.getBean(dataSourceName);
            if(o == null){
                return namedParameterJdbcTemplate;
            }else{
                DataSource ds = (DataSource)o;
                return  new NamedParameterJdbcTemplate(ds);
            }
        }
    }

    public JdbcTemplate getJdbcTemplate(String dataSourceName) {
        if(dataSourceName == null || "".equalsIgnoreCase(dataSourceName)){
            return jdbcTemplate;
        }else{
            Object o =  applicationContext.getBean(dataSourceName);
            if(o == null){
                return jdbcTemplate;
            }else{
                DataSource ds = (DataSource)o;
                return  new JdbcTemplate(ds);
            }
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
