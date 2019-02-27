package com.hsy.platform.dao;

import com.hsy.platform.plugin.SimpleRowMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcDao implements ApplicationContextAware {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    /**
     * 根据sql查询map
     * @param sql
     * @return
     */
    public Map<String,Object> queryMapBySql(String sql){
        return jdbcTemplate.queryForMap(sql,new SimpleRowMapper());
    }


    /**
     * 根据sql 查询 Map
     * @param sql
     * @param param
     * @return
     */
    public Map<String,Object> queryMapBySql(String sql,Object...param){
        return jdbcTemplate.queryForMap(sql,param,new SimpleRowMapper());
    }


    /**
     * 根据sql 查询 Map（驼峰）
     * @param sql
     * @param param
     * @return
     */
    public Map<String,Object> queryMapBySql(String sql,Map<String,Object> param){
        return namedParameterJdbcTemplate.queryForObject(sql,param,new SimpleRowMapper());
    }

    /**
     * 根据sql 查询 List<Map>
     * @param sql
     * @param param
     * @return
     */
    public List<Map<String,Object>> queryMapListBySql(String sql, Object...param){
        return jdbcTemplate.queryForList(sql,param,new SimpleRowMapper());
    }

    /**
     * 根据sql 查询 List<Map>
     * @param sql
     * @param param
     * @return
     */
    public List<Map<String,Object>> queryMapListBySql(String sql,Map<String,Object> param){
        return namedParameterJdbcTemplate.query(sql,param,new SimpleRowMapper());
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


    /**
     * 根据sql查询数量
     * @param sql
     * @param param
     * @return
     */
    public int getCountBySql(String sql,Object...param){
        return jdbcTemplate.queryForObject(sql,param,Integer.class);
    }

}
