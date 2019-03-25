package com.hsy.platform.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	public static String get14DateTimeId() {
		String s =  DateUtil.formatDateTime(new Date());
		return s.replace(" ","").replace("-","").replace(":","");
	}


	public static String getTimeUUID(){
		String pre = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return new StringBuffer(pre).append(StringUtils.substring(get32UUID(),14)).toString();
	}

}

