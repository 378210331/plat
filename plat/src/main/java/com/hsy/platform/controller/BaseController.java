package com.hsy.platform.controller;

import com.hsy.platform.plugin.Page;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseController{
    public static final String SAVE_SUCCESS = "保存成功";
    public static final String SAVE_FAILURE = "保存失败";
    public static final String UPDATE_SUCCESS = "更新成功";
    public static final String UPDATE_FAILURE = "更新失败";
    public static final String DELETE_SUCCESS = "删除成功";
    public static final String DELETE_FAILURE = "删除失败";

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public BaseController() {
        logger.debug("controller:" + getClass().getSimpleName() + "Controller已加载");
    }

    public abstract ModelAndView init() throws Exception;

    public abstract ModelAndView onEdit() throws Exception;

    public abstract Page list(Page page) throws Exception;

    public abstract Map<String, Object> delete() throws Exception;

    public abstract Map<String, Object> update() throws Exception;

    public abstract Map<String, Object> save() throws Exception;


    public ModelAndView getAutoListView() {
        ModelAndView mv = new ModelAndView();
        String uri = this.getRequest().getRequestURI();
        uri = uri.substring(0, uri.lastIndexOf("/"));
        uri = uri.substring(uri.lastIndexOf("/"), uri.length());
        StringBuilder sb = new StringBuilder(uri + uri);
        sb.append("_list");
        mv.setViewName(sb.toString());
        return mv;
    }

    public ModelAndView getAutoEditView() {
        ModelAndView mv = new ModelAndView();
        String uri = this.getRequest().getRequestURI();
        uri = uri.substring(0, uri.lastIndexOf("/"));
        uri = uri.substring(uri.lastIndexOf("/"), uri.length());
        StringBuilder sb = new StringBuilder(uri + uri);
        sb.append("_edit");
        mv.setViewName(sb.toString());
        return mv;
    }

    public ModelAndView getAutoGridView() {
        ModelAndView mv = new ModelAndView();
        String uri = this.getRequest().getRequestURI();
        uri = uri.substring(0, uri.lastIndexOf("/"));
        uri = uri.substring(uri.lastIndexOf("/"), uri.length());
        StringBuilder sb = new StringBuilder(uri + uri);
        sb.append("_grid");
        mv.setViewName(sb.toString());
        return mv;
    }

    public ModelAndView getAutoDetailView(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String uri = request.getRequestURI();
        uri = uri.substring(0, uri.lastIndexOf("/"));
        uri = uri.substring(uri.lastIndexOf("/"), uri.length());
        StringBuilder sb = new StringBuilder(uri + uri);
        sb.append("_detail");
        mv.setViewName(sb.toString());
        return mv;
    }

    public Map<String, Object> getResultMap(boolean res, String msg) {
        HashMap<String, Object> map = new HashMap();
        map.put("res", Boolean.valueOf(res));
        map.put("msg", msg);
        return map;
    }

    public Map<String, Object> getDataMap(boolean res, Object data,Page page) {
        HashMap<String, Object> map = new HashMap();
        if(res){
            map.put("code",0);
        }
        map.put("res",res);
        if(page != null){
            map.put("count",page.getTotal());
        }
        map.put("data", data);
        return map;
    }

    public Map<String, Object> getDataMap(boolean res, Object data) {
        HashMap<String, Object> map = new HashMap();
        if(res){
            map.put("code",0);
        }
        map.put("res",res);
        map.put("data", data);
        return map;
    }

    /**
     * 得到PageData
     */
    public PageData getPageData(){
        return new PageData(this.getRequest());
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    /**
     * 得到32位的uuid
     * @return
     */
    public String get32UUID(){

        return UuidUtil.get32UUID();
    }

    /**
     * 得到分页列表的信息
     */
    public Page getPage(){

        return new Page();
    }


}
