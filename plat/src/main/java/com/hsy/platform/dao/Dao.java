package com.hsy.platform.dao;

import com.hsy.platform.plugin.PageData;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;



public interface Dao {

	/**
	 * 保存
	 * @param statement
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int save(String statement, Object obj)throws Exception;
	
	/**
	 * 批量保存
	 * @param sqlKey
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public int batchSave(String sqlKey, List objs)throws Exception;
	
	/**
	 * 更新
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int update(String sqlKey, Object obj) throws Exception;
	
	/**
	 * 批量更新
	 * @param sqlKey
	 * @param objs
	 * @throws Exception
	 */
	public void batchUpdate(String sqlKey, List objs)throws Exception;
	
	/**
	 * 删除
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int delete(String sqlKey, Object obj) throws Exception;
	
	/**
	 * 批量删除
	 * @param sqlKey
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public void batchDelete(String sqlKey, List objs)throws Exception;
	
	/**
	 * 获取Map
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryMapByKey(String sqlKey, Object obj) throws Exception;
	
	/**
	 * 获取pageData
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public PageData queryPageDataByKey(String sqlKey, Object obj) throws Exception;
	
	/**
	 * 获取Map
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryMapListByKey(String sqlKey, Object obj) throws Exception;
	
	/**
	 * 获取pageDataList
	 * @param sqlKey
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryPageDataListByKey(String sqlKey, Object obj) throws Exception;
	

	/**
	 * 获取记录数
	 * @param sqlKey
	 * @param obj
	 * @return
	 */
	public int getCountByKey(String sqlKey, Object obj);
	

	public SqlSession getSqlSession();
}
