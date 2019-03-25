package com.hsy.platform.listener;


import com.hsy.platform.common.Const;
import com.hsy.platform.plugin.Cache.PlatCacheManager;
import com.hsy.platform.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化监听
 */
public class WebAppContextListener implements ServletContextListener{



	public static Logger log = LoggerFactory.getLogger(WebAppContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent event) {
		log.info("系统初始化");
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());

		if("true".equalsIgnoreCase(PropertiesUtils.getValueByKey("platCacheManager"))){
			PlatCacheManager platCacheManager = (PlatCacheManager) Const.WEB_APP_CONTEXT.getBean("platCacheManager");
			try {
				platCacheManager.cacheInit();
			} catch (Exception e) {
				log.error("启动系统平台缓存管理器失败{}",e.getMessage());
			}
		}else{
			log.info("未启动系统平台缓存管理器");
		}

	}

}
