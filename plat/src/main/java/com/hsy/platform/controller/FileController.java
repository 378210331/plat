package com.hsy.platform.controller;


import com.hsy.platform.plugin.LayPage;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.FileService;
import com.hsy.platform.utils.PropertiesUtils;
import com.hsy.platform.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 * 文件资源映射
 */
@RestController
@RequestMapping("/file/")
public class FileController extends  BaseController{

    @Resource
    FileService fileService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public static String PDF_PATH  = PropertiesUtils.getValueByKey("pdf_path");

    public static String IMG_PATH  = PropertiesUtils.getValueByKey("img_path");

    public static String DEFAULT_PATH = PropertiesUtils.getValueByKey("defalut_path")==null?"":PropertiesUtils.getValueByKey("defalut_path");


    @RequestMapping("upload")
    public Map<String,Object> upload(HttpServletRequest request) throws Exception {
        String id = UuidUtil.getTimeUUID();
        PageData pd = new PageData();
        MultipartRequest mtRequest = (MultipartRequest) request;
        MultipartFile file = mtRequest.getFile("file");
        if (file == null) return getResultMap(false, "无法获取文件");
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String realName = id + suffix;
        String storePath = "";
        if(".pdf".equals(suffix)){
            storePath = PDF_PATH;
        }else if(".png".equals(suffix) || ".jpg".equals(suffix)){
            storePath = IMG_PATH;
        }else{
            storePath = DEFAULT_PATH;
        }
        File storeFile = new File(storePath + File.separator + realName);
        file.transferTo(storeFile);
        pd.addParam("fileName", fileName);
        pd.addParam("suffix", suffix);
        pd.addParam("realName", realName);
        pd.addParam("uuid", id);
        fileService.save(pd);
        return getResultMap(true, id);
    }



    /**
     * 获取pdf 文件预览
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("pdf/{id}")
    public void  pdf(@PathVariable  String id,HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        if(StringUtils.isBlank(id)){
            logger.error("文件id不能为空");
            return ;
        }
        PageData pd  = fileService.getById(id);
        if(pd == null){
            logger.error("未能找到相关记录");
            return ;
        }
        String filename = pd.getString("fileName");
        String realName = pd.getString("realName");
        if(StringUtils.isBlank(PDF_PATH)){
            logger.error("未定义pdf存放路径变量pdf_path");
            return;
        }
        String fileRealPath = PDF_PATH + File.separator + realName;
        File file = new File(fileRealPath);
        if(!file.exists()){
            logger.error("未找到文件:{}",fileRealPath);
            return ;
        }
        URL u = new URL("file:///" + file.getAbsolutePath());
        String contentType = u.openConnection().getContentType();
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline;filename=" + filename);
       InputStream fis = new BufferedInputStream(new FileInputStream(fileRealPath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(buffer);
        out.flush();
        out.close();
    }


    /**
     * 获取图片预览
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("img/{id}")
    public void img(@PathVariable  String id,HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        if(StringUtils.isBlank(id)){
            logger.error("文件id不能为空");
            return ;
        }
        PageData pd  = fileService.getById(id);
        if(pd == null){
            logger.error("未能找到相关记录");
            return ;
        }
        String filename = pd.getString("fileName");
        String realName = pd.getString("realName");
        if(StringUtils.isBlank(IMG_PATH)){
            logger.error("未定义pdf存放路径变量img_path");
            return ;
        }
        String fileRealPath = IMG_PATH + File.separator + realName;
        File file = new File(fileRealPath);
        if(!file.exists()){
            logger.error("未找到文件:{}",fileRealPath);
            return ;
        }
        URL u = new URL("file:///" + file.getAbsolutePath());
        String contentType = u.openConnection().getContentType();
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline;filename=" + filename);
        InputStream fis = new BufferedInputStream(new FileInputStream(fileRealPath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(buffer);
        out.flush();
        out.close();
    }

    @Override
    public ModelAndView init() throws Exception {
        return null;
    }

    @Override
    public ModelAndView onEdit() throws Exception {
        return null;
    }

    @Override
    public LayPage list(LayPage page) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> delete() throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> update() throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> save() throws Exception {
        return null;
    }
}
