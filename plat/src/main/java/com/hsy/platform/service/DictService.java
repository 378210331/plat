package com.hsy.platform.service;

import com.hsy.platform.plugin.PageData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictService extends BaseService{

	
	public String getMapperName() {
		return "DictMapper";
	}
	/**
	 * 根据传入的值返回对应的键值对
	 * @param key 查询对应的表
	 * @return  对应的key和values值 PageData对象
	 * @throws Exception
	 */
	@Cacheable("simpleCache")
	public List<PageData>  getDictSelect(String key) throws Exception {
		List<PageData> data=getDao().queryPageDataListByKey(getMapperName()+".getList", key);
		return data;
	}

	@Cacheable("simpleCache")
	public List<PageData>  getDictTree(String key,String start) throws Exception {
		PageData pd = new PageData();
		pd.addParam("key",key);
		pd.addParam("start",start);
		List<PageData> data=getDao().queryPageDataListByKey(getMapperName()+".getTreeList", pd);
		return data;
	}

}
