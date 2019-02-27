package com.hsy.platform.controller;

import com.hsy.platform.plugin.LayPage;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.DeptService;
import com.hsy.platform.service.UserService;
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
@RequestMapping("/user/")
public class UserController extends BaseController {

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    UserService userService;

    @Resource
    DeptService deptService;

    @RequestMapping("init")
    public ModelAndView init() throws  Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("security/user_list");
        List<PageData> deptList =deptService.getPageDataList(null);
        mv.addObject("deptList",deptList);
        return mv;
    }

    @RequestMapping("onEdit")
    public ModelAndView onEdit() throws Exception {
        ModelAndView mv = new ModelAndView("security/user_edit");
        List<PageData> deptList =deptService.getPageDataList(null);
        mv.addObject("deptList",deptList);
        mv.addObject("oid",getRequest().getParameter("userId"));
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public LayPage list(LayPage page) throws Exception {
        page.setPd(this.getPageData());
        page = userService .listPage(page);
        return page ;
    }

    @RequestMapping("query")
    @ResponseBody
    public Map<String,Object> query() throws Exception {
        PageData pd = getPageData();
        List<PageData>  list =  userService.getPageDataList(pd);
        return this.getDataMap(true,list);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete() throws Exception {
        PageData pd = getPageData();
        userService.delete(pd);
        return getResultMap(true,DELETE_SUCCESS);
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save() throws Exception {
        PageData pd = getPageData();
        try {
            userService.save(pd);
        }catch (Exception e){
            return getResultMap(false,e.getMessage());
        }
        return getResultMap(true,SAVE_SUCCESS);
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update() throws Exception {
        PageData pd = getPageData();
        userService.update(pd);
        return getResultMap(true,UPDATE_SUCCESS);
    }



}
