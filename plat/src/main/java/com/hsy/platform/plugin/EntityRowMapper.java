package com.hsy.platform.plugin;

import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 * rowMapper公共类
 * 利用java反射完成自动写入
 * @author husiyi
 */
public class EntityRowMapper<T> implements RowMapper<T> {
 
    /**
     * 添加字段注释.
     */
    private Class<?> targetClazz;
 
    /**
     * 添加字段注释.
     */
    private HashMap<String, Field> fieldMap;
 
    /**
     * 构造函数.
     * 
     * @param targetClazz
     *            
     */
    public EntityRowMapper(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
        fieldMap = new HashMap<String, Field>();
        Field[] fields = targetClazz.getDeclaredFields();
        for (Field field : fields) {
        	String name = field.getName();
        	String columnName = getColumnName(name);
            fieldMap.put(columnName, field);
        }
    }
 
    @Override
    public T mapRow(ResultSet rs, int arg1) throws SQLException {
        T obj = null;
        String columnName = null;
        try {
            obj = (T) targetClazz.newInstance();
 
            final ResultSetMetaData metaData = rs.getMetaData();
            int columnLength = metaData.getColumnCount();
            for (int i = 1; i <= columnLength; i++) {
                columnName = metaData.getColumnName(i);
                if("RN".equals(columnName))continue;
                Class fieldClazz = fieldMap.get(columnName).getType();
                Field field = fieldMap.get(columnName);
                field.setAccessible(true);
 
                if (fieldClazz == int.class || fieldClazz == Integer.class) { // int
                    field.set(obj, rs.getInt(columnName));
                } else if (fieldClazz == boolean.class || fieldClazz == Boolean.class) { // boolean
                    field.set(obj, rs.getBoolean(columnName));
                } else if (fieldClazz == String.class) { // string
                    field.set(obj, rs.getString(columnName));
                } else if (fieldClazz == float.class) { // float
                    field.set(obj, rs.getFloat(columnName));
                } else if (fieldClazz == double.class || fieldClazz == Double.class) { // double
                    field.set(obj, rs.getDouble(columnName));
                } else if (fieldClazz == BigDecimal.class) { // bigdecimal
                    field.set(obj, rs.getBigDecimal(columnName));
                } else if (fieldClazz == short.class || fieldClazz == Short.class) { // short
                    field.set(obj, rs.getShort(columnName));
                } else if (fieldClazz == Date.class) { // date
                    field.set(obj, rs.getDate(columnName));
                } else if (fieldClazz == Timestamp.class) { // timestamp
                    field.set(obj, rs.getTimestamp(columnName));
                } else if (fieldClazz == Long.class || fieldClazz == long.class) { // long
                    field.set(obj, rs.getLong(columnName));
                }
 
                field.setAccessible(false);
            }
        } catch (Exception e) {
        	 System.err.println("转换错误，错误字段为"+columnName);
        }
 
        return obj;
    }
 
    /**
     * 获取大写的字段名称
     * @param fieldName
     * @return
     */
    private String getColumnName(String fieldName){
    	char[] nameArray = fieldName.toCharArray();
    	StringBuilder sb = new StringBuilder();
    	for(char c : nameArray){
    		if(c>='A' && c<='Z'){
    			sb.append("_");
    		}else  if (c >= 'a' && c <= 'z'){
    			c -= 32;
    		}
    	sb.append(c);
    	}
    	return sb.toString();
    }
}