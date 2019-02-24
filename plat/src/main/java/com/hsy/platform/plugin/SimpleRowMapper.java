package com.hsy.platform.plugin;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * rowMapper公共类
 * 返回驼峰字段类型
 * @author husiyi
 */
public class SimpleRowMapper implements RowMapper<Map<String,Object>> {

	public Map<String,Object> mapRow(ResultSet rs, int arg1) throws SQLException {
		 Map<String,Object> obj = new HashMap<String,Object>();
	        String columnName = null;
	        try {
	 
	            final ResultSetMetaData metaData = rs.getMetaData();
	            int columnLength = metaData.getColumnCount();
	            for (int i = 1; i <= columnLength; i++) {
	                columnName = metaData.getColumnName(i);
	                if("RN".equals(columnName))continue;
	                Object Value = rs.getObject(columnName);
	                if (Value instanceof Integer) { // int
	                	obj.put(getColumnName(columnName), rs.getInt(columnName));
	                } else if (Value instanceof Boolean) { // boolean
	                    obj.put(getColumnName(columnName), rs.getBoolean(columnName));
	                } else if (Value instanceof String) { // string
	                    obj.put(getColumnName(columnName), rs.getString(columnName));
	                } else if (Value instanceof Float) { // float
	                    obj.put(getColumnName(columnName), rs.getFloat(columnName));
	                } else if (Value instanceof Double) { // double
	                    obj.put(getColumnName(columnName), rs.getDouble(columnName));
	                } else if (Value instanceof BigDecimal) { // bigdecimal
	                    obj.put(getColumnName(columnName), rs.getBigDecimal(columnName));
	                } else if ( Value instanceof Short) { // short
	                    obj.put(getColumnName(columnName), rs.getShort(columnName));
	                } else if (Value instanceof Timestamp) { // timestamp
	                    obj.put(getColumnName(columnName), rs.getTimestamp(columnName));
	                } else if (Value instanceof Date) { // date
	                    obj.put(getColumnName(columnName), rs.getDate(columnName));
	                } else if (Value instanceof Long) { // long
	                    obj.put(getColumnName(columnName), rs.getLong(columnName));
	                }else{
	                	obj.put(getColumnName(columnName), rs.getObject(columnName));
	                }
	            }
	        } catch (Exception e) {
	        	 System.err.println("转换错误，错误字段为"+columnName);
	        	 obj.put(getColumnName(columnName), rs.getObject(columnName));
	        }
	 
	        return obj;
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
    		if("".equals("s"))continue;
    		String s = arr[i];
    		sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
    	}
    	return sb.toString();
    }

}
