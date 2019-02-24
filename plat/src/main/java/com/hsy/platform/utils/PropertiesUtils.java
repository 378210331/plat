package com.hsy.platform.utils;

import com.hsy.platform.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    static final  Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    public  static  String  FILE_PATH = getFilePath();
    static  final  String  DEFAULT_FILENAME= "system.properties";

    public static String getFilePath(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().contains("win")){
            FILE_PATH = PropertiesUtils.class.getResource("/").getPath().replace("test-classes","classes");
        }else{
            String classPath  = PropertiesUtils.class.getClassLoader().getResource("/").getPath();
            classPath = classPath.replace("\\","/");
            FILE_PATH = classPath;
        }
        return FILE_PATH;
    }


    /**
     * 获取系统资源文件
     * @return
     */
    public static Properties getProperties(){
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(FILE_PATH + DEFAULT_FILENAME);
            props.load(in);
        }catch (Exception e){
            logger.error("获取资源文件失败");
        }
        return props;
    }

    /**
     * 按名称获取系统资源文件
     * @return
     */
    public static Properties getProperties(String filename){
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(FILE_PATH + filename);
            props.load(in);
        }catch (Exception e){
            logger.error("获取资源文件失败");
        }
        return props;
    }


    /**
     * 按key获取资源值
     * @param key
     * @return
     */
    public static String getValueByKey(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }else {
            Properties props = new Properties();
            InputStream in = null;
            try {
                in = new FileInputStream(FILE_PATH + DEFAULT_FILENAME);
                props.load(in);
                String value = new String(props.getProperty(key).getBytes("ISO-8859-1"), "UTF-8");
                in.close();
                return value;

            } catch (IOException e) {
                logger.warn("获取资源值错误" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 按key和文件名称获取资源值
     * @param key
     * @param file_name
     * @return
     */
    public static String getValueByKey(String key,String file_name){
        if(StringUtils.isBlank(key)){
            return null;
        }else {
            Properties props = new Properties();
            InputStream in = null;
            try {
                in = new FileInputStream(FILE_PATH + file_name);
                props.load(in);
                String value = props.getProperty(key);
                // 有乱码时要进行重新编码
                // new String(props.getProperty("name").getBytes("ISO-8859-1"), "UTF-8");
                in.close();
                return value;

            } catch (IOException e) {
                logger.warn("获取资源值错误" + e.getMessage());
                return null;
            }
        }
    }

}
