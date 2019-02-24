package com.hsy.platform.dao;

import com.hsy.platform.plugin.PageData;
import com.hsy.platform.plugin.SimpleRowMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("DaoSupport")
public class DaoSupport implements Dao {


	private static final Logger logger = LoggerFactory.getLogger(DaoSupport.class);
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	
	public int save(String statement, Object obj)throws Exception {
		return sqlSessionTemplate.insert(statement, obj);
	}
	
	public int batchSave(String sqlKey, List objs )throws Exception{
		return sqlSessionTemplate.insert(sqlKey, objs);
	}
	
	public int update(String sqlKey, Object obj) throws Exception {
		return sqlSessionTemplate.update(sqlKey, obj);
	}
	
	public void batchUpdate(String sqlKey, List objs)throws Exception{
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null){
				for(int i=0,size=objs.size();i<size;i++){
					sqlSession.update(sqlKey, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}finally{
			sqlSession.close();
		}
	}

	public int delete(String sqlKey, Object obj )throws Exception{
		return sqlSessionTemplate.delete(sqlKey, obj);
	}

	public void batchDelete(String sqlKey, List objs) throws Exception {
			SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
			//批量执行器
			SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
			try{
				if(objs!=null){
					for(int i=0,size=objs.size();i<size;i++){
						sqlSession.delete(sqlKey, objs.get(i));
					}
					sqlSession.flushStatements();
					sqlSession.commit();
					sqlSession.clearCache();
				}
			}finally{
				sqlSession.close();
			}
	}

	public Map<String,Object> queryMapByKey(String sqlKey, Object obj) throws Exception {
		return sqlSessionTemplate.selectOne(sqlKey, obj);
	}
	
	public PageData queryPageDataByKey(String sqlKey, Object obj) throws Exception{
		return sqlSessionTemplate.selectOne(sqlKey,obj);
	}
	
	public List<Map<String,Object>> queryMapListByKey(String sqlKey, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(sqlKey, obj);
	}
	
	public List<PageData> queryPageDataListByKey(String sqlKey, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(sqlKey, obj);
	}
	
	public Map<String,Object> queryMapBySql(String sql){
		return jdbcTemplate.queryForMap(sql);
	}

	public Map<String,Object> queryMapBySql(String sql,Object...param){
		return jdbcTemplate.queryForMap(sql,param);
	}

	public List<Map<String,Object>> queryMapListBySql(String sql,Object...param){
		return jdbcTemplate.queryForList(sql,param,new SimpleRowMapper());
	}

	public int getCountByKey(String sqlKey,Object obj){
		PageData pg = sqlSessionTemplate.selectOne(sqlKey, obj);
		return Integer.parseInt(pg.get("totalcount")==null?"0":pg.get("totalcount").toString());
	}
	
	public int getCountBySql(String sql,Object...param){
		return jdbcTemplate.queryForObject(sql,param,Integer.class);
	}


	public SqlSession getSqlSession() {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
}
