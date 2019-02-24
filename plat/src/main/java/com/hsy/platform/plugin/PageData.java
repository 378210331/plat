package com.hsy.platform.plugin;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class PageData extends HashMap implements Map{
	
	private static final long serialVersionUID = 1L;
	
	Map map = null;
	HttpServletRequest request;


	Page page;//作为参数接受来自拦截器返回的page信息
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}



	public PageData(HttpServletRequest request){
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator entries = properties.entrySet().iterator(); 
		Entry entry;
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value); 
		}
		map = returnMap;
	}
	
	public PageData() {
		map = new HashMap();
	}
	
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	public Map getMap(){
		return this.map;
	}


	public String getString(Object key) {
		return (String)get(key);
	}


	@SuppressWarnings("unchecked")
	@Override//返回驼峰写法
	public Object put(Object key, Object value) {
		String keyString = key.toString().toLowerCase();
		if(keyString.contains("_")){
			String[] keyArray = keyString.split("_");
			StringBuilder sb = new StringBuilder(keyArray[0]);
			for(int i=1;i<keyArray.length;i++){
				String keyItem = keyArray[i];
				if("".equals(keyItem))continue;
				keyItem = keyItem.substring(0,1).toUpperCase()+keyItem.substring(1,keyItem.length());
				sb.append(keyItem);
			}
			keyString = sb.toString();
		}
		if(value ==null) value="";
		return map.put(keyString, value);
	}

	/**
	 * 添加参数，避免原驼峰写法的字段被小写化
	 * @param key
	 * @param value
	 */
	 public PageData addParam(Object key, Object value){
		  map.put(key, value);
		  return this;
	 }
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		
		return map.containsValue(value);
	}

	public Set entrySet() {
		return map.entrySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set keySet() {
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		map.putAll(t);
	}

	public int size() {
		return map.size();
	}

	public Collection values() {
		return map.values();
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator entries = map.entrySet().iterator();
		Entry entry = null;
		//遍历map,并将map中的key,value写入sb中
		while(entries.hasNext()){
			entry = (Entry) entries.next();
			if(null!=entry){
				sb.append((String)entry.getKey()+":");
				Object value = entry.getValue();
				if(value instanceof Timestamp || value instanceof Date) {
					sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entry.getValue())+"\n");
				}else if(value instanceof BigDecimal){
					BigDecimal bigValue = (BigDecimal)value;
					sb.append(bigValue.floatValue()+"\n");
				}else if(value instanceof String){
					sb.append(value.toString()+"\n");
				}else{
					sb.append("\n");
				}
				entry = null;
			}
		}
		return sb.toString();
	}
	
}
