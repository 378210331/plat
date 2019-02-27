package com.hsy.platform.dao;

import com.hsy.platform.plugin.PageData;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository("DaoSupport")
public class DaoSupport implements Dao {


	private static final Logger logger = LoggerFactory.getLogger(DaoSupport.class);
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 新增记录
	 * @param statement
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int save(String statement, Object obj)throws Exception {
		return sqlSessionTemplate.insert(statement, obj);
	}

	/**
	 * 批量新增
	 * @param sqlKey
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public int batchSave(String sqlKey, List objs )throws Exception{
		return sqlSessionTemplate.insert(sqlKey, objs);
	}

	/**
	 * 更新记录
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int update(String sqlKey, Object obj) throws Exception {
		return sqlSessionTemplate.update(sqlKey, obj);
	}

	/**
	 * 批量更新
	 * @param sqlKey
	 * @param objs
	 * @throws Exception
	 */
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

	/**
	 * 删除记录
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int delete(String sqlKey, Object obj )throws Exception{
		return sqlSessionTemplate.delete(sqlKey, obj);
	}

	/**
	 * 批量删除
	 * @param sqlKey
	 * @param objs
	 * @throws Exception
	 */
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

	/**
	 * 根据key 查询map对象（驼峰）
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryMapByKey(String sqlKey, Object obj) throws Exception {
		Map<String,Object>  map = sqlSessionTemplate.selectOne(sqlKey, obj);
		Map<String,Object> result = new HashMap<>(map.size());
		for(Map.Entry<String,Object> entry : map.entrySet()){
			result.put(getColumnName(entry.getKey()),entry.getValue());
		}
		return  result;
	}

	/**
	 * 根据key 查询pd对象
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public PageData queryPageDataByKey(String sqlKey, Object obj) throws Exception{
		return sqlSessionTemplate.selectOne(sqlKey,obj);
	}

	/**
	 * 根据key 查询Object对象
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object queryByKey(String sqlKey, Object obj) throws Exception{
		return (Object)sqlSessionTemplate.selectOne(sqlKey,obj);
	}

	/**
	 * 根据key 查询List<Map> （驼峰）
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryMapListByKey(String sqlKey, Object obj) throws Exception {
		List<Map<String,Object>> list =  sqlSessionTemplate.selectList(sqlKey, obj);
		List<Map<String,Object>> resultList =  new LinkedList<>();
		for(Map<String,Object> map : list){
			Map<String,Object> result = new HashMap<>(map.size());
			for(Map.Entry<String,Object> entry : map.entrySet()){
				result.put(getColumnName(entry.getKey()),entry.getValue());
			}
			resultList.add(result);
		}
		return resultList;
	}

	/**
	 * 根据Key 查询List<pd>
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryPageDataListByKey(String sqlKey, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(sqlKey, obj);
	}


	/**
	 * 根据key获取数量
	 * @param sqlKey
	 * @param obj
	 * @return
	 */
	public int getCountByKey(String sqlKey,Object obj){
		PageData pg = sqlSessionTemplate.selectOne(sqlKey, obj);
		return Integer.parseInt(pg.get("totalcount")==null?"0":pg.get("totalcount").toString());
	}



	public SqlSession getSqlSession() {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}



	/**
	 * 获取大写的字段名称
	 * @param fieldName
	 * @return
	 */
	private String getColumnName(String fieldName){
		fieldName = fieldName.toLowerCase();
		String[] arr = fieldName.split("_");
		StringBuilder sb = new StringBuilder(arr[0]);
		for(int i=1;i<arr.length;i++){
			if(StringUtils.isBlank(arr[i]))continue;
			String s = arr[i];
			sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
		}
		return sb.toString();
	}

}
