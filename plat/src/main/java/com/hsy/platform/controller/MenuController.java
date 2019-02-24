package com.hsy.platform.controller;

import com.hsy.platform.plugin.Page;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.MenuService;
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

@RequestMapping("/menu/")
@Controller
public class MenuController extends  BaseController {

    static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Resource
    MenuService menuService;

    @Resource
    SystemService systemService;

    @RequestMapping("getRoleMenu")
    @ResponseBody
    public List<Map<String,Object>> getRoleMenu(){
        return null;
    }

    @RequestMapping("init")
    public ModelAndView init() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("security/menu_list");
        List<PageData> systemList = systemService.getPageDataList(null);
        mv.addObject("systemList",systemList);
        return mv;
    }

    @RequestMapping("onEdit")
    public ModelAndView onEdit() throws Exception {
        ModelAndView mv = new ModelAndView("security/menu_edit");
        mv.addObject("menuId",getRequest().getParameter("menuId"));
        mv.addObject("newId", UuidUtil.get32UUID());
        List<PageData> systemList = systemService.getPageDataList(null);
        PageData param = new PageData().addParam("menuType","1");
        List<PageData> pMenuList = menuService.getPageDataList(param);
        mv.addObject("systemList",systemList);
        mv.addObject("pMenuList",pMenuList);
        return mv;
    }


    @RequestMapping("list")
    @ResponseBody
    public Page list(Page page) throws Exception {
        page.setPd(this.getPageData());
        page = menuService.listPage(page);
        return page ;
    }

    @RequestMapping("query")
    @ResponseBody
    public Map<String,Object> query() throws Exception {
        PageData pd = getPageData();
          List<PageData>  list =  menuService.getPageDataList(pd);
          return this.getDataMap(true,list);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete() throws Exception {
         PageData pd = getPageData();
            menuService.delete(pd);
            return getResultMap(true,DELETE_SUCCESS);
        }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save() throws Exception {
        PageData pd = getPageData();
        menuService.save(pd);
        return getResultMap(true,SAVE_SUCCESS);
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update() throws Exception {
        PageData pd = getPageData();
        menuService.update(pd);
        return getResultMap(true,UPDATE_SUCCESS);
    }

}
