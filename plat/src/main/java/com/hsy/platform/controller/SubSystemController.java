package com.hsy.platform.controller;

import com.hsy.platform.plugin.Page;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.SystemService;
import com.hsy.platform.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/")
public class SubSystemController extends BaseController {

    static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    SystemService systemService;

    @RequestMapping("init")
    public ModelAndView init(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("security/system_list");
        return mv;
    }

    @RequestMapping("onEdit")
    public ModelAndView onEdit() throws Exception {
        ModelAndView mv = new ModelAndView("security/system_edit");
        mv.addObject("systemId",getRequest().getParameter("systemId"));
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public Page list(Page page) throws Exception {
        page.setPd(this.getPageData());
        page =systemService .listPage(page);
        return page ;
    }

    @RequestMapping("query")
    @ResponseBody
    public Map<String,Object> query() throws Exception {
        PageData pd = getPageData();
        List<PageData>  list =  systemService.getPageDataList(pd);
        return this.getDataMap(true,list);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete() throws Exception {
        PageData pd = getPageData();
        List<PageData> menuList = systemService.getMenuBySystem(pd);
        if(menuList.size()  >0){
            return getResultMap(false,"该子系统关联"+menuList.size()+"个菜单功能,请先删除关联");
        }
        systemService.delete(pd);
        return getResultMap(true,DELETE_SUCCESS);
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save() throws Exception {
        PageData pd = getPageData();
        pd.addParam("systemId", UuidUtil.get32UUID());
        systemService.save(pd);
        return getResultMap(true,SAVE_SUCCESS);
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update() throws Exception {
        PageData pd = getPageData();
        systemService.update(pd);
        return getResultMap(true,UPDATE_SUCCESS);
    }



}
