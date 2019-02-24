package com.hsy.platform.utils;

import cn.hutool.core.date.DateUtil;

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


}

