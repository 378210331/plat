package com.hsy.platform.plugin;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 下拉选择映射文件-解决queryForList的大小写问题
 * @author husiyi
 *
 */
public class SelectMapper  implements RowMapper<Map<String,Object>> {
	
	public Map<String,Object> mapRow(ResultSet arg0, int arg1) throws SQLException {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			result.put("code", arg0.getObject("code"));
		}catch (Exception e) {
		}
		try{
			result.put("label", arg0.getObject("label"));
		}catch (Exception e) {
		}
		try{
			result.put("value", arg0.getObject("value"));
		}catch (Exception e) {
		}
		try{
			result.put("checked", arg0.getObject("checked"));
		}catch (Exception e) {
		}
		try{
			result.put("dept_bz", arg0.getObject("dept_bz"));
		}catch (Exception e) {
		}
		return result;
	}
}

