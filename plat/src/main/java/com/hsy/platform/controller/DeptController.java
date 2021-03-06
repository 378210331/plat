package com.hsy.platform.controller;

import com.hsy.platform.plugin.LayPage;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.DeptService;
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
@RequestMapping("/dept/")
public class DeptController extends BaseController {

    static final Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Resource
    DeptService deptService;

    @RequestMapping("init")
    public ModelAndView init(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("security/dept_list");
        return mv;
    }

    @RequestMapping("onEdit")
    public ModelAndView onEdit() throws Exception {
        ModelAndView mv = new ModelAndView("security/dept_edit");
        mv.addObject("oid",getRequest().getParameter("deptDm"));
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public LayPage list(LayPage page) throws Exception {
        page.setPd(this.getPageData());
        page =deptService .listPage(page);
        return page ;
    }

    @RequestMapping("query")
    @ResponseBody
    public Map<String,Object> query() throws Exception {
        PageData pd = getPageData();
        List<PageData>  list =  deptService.getPageDataList(pd);
        return this.getDataMap(true,list);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete() throws Exception {
        PageData pd = getPageData();
        deptService.delete(pd);
        return getResultMap(true,DELETE_SUCCESS);
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save() throws Exception {
        PageData pd = getPageData();
        deptService.save(pd);
        return getResultMap(true,SAVE_SUCCESS);
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update() throws Exception {
        PageData pd = getPageData();
        deptService.update(pd);
        return getResultMap(true,UPDATE_SUCCESS);
    }

    @RequestMapping("getSwjgTree")
    @ResponseBody
    public List<PageData> getSwjgTree()throws Exception {
        PageData pd = getPageData();
        return deptService.getDeptTree(pd);
    }

}
