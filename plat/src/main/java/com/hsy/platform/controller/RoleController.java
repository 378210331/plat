package com.hsy.platform.controller;

import com.hsy.platform.dao.DaoSupport;
import com.hsy.platform.plugin.LayPage;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.RoleService;
import com.hsy.platform.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/role/")
public class RoleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);


    @Resource
    RoleService roleService;

    @RequestMapping("init")
    public ModelAndView init(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("security/role_list");
        return mv;
    }

    @RequestMapping("onEdit")
    public ModelAndView onEdit() throws Exception {
        ModelAndView mv = new ModelAndView("security/role_edit");
        mv.addObject("roleId",getRequest().getParameter("roleId"));
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public LayPage list(LayPage page) throws Exception {
        page.setPd(this.getPageData());
        page = roleService .listPage(page);
        return page ;
    }

    @RequestMapping("query")
    @ResponseBody
    public Map<String,Object> query() throws Exception {
        PageData pd = getPageData();
        List<PageData>  list =  roleService.getPageDataList(pd);
        return this.getDataMap(true,list);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete() throws Exception {
        PageData pd = getPageData();
        roleService.delete(pd);
        return getResultMap(true,DELETE_SUCCESS);
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save() throws Exception {
        PageData pd = getPageData();
        pd.addParam("roleId", UuidUtil.get32UUID());
        roleService.save(pd);
        return getResultMap(true,SAVE_SUCCESS);
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update() throws Exception {
        PageData pd = getPageData();
        roleService.update(pd);
        return getResultMap(true,UPDATE_SUCCESS);
    }

    @RequestMapping("onRelation")
    public ModelAndView onRelation(){
        ModelAndView mv = new ModelAndView("security/role_relation");
        mv.addObject("roleId",getRequest().getParameter("roleId"));
        return mv;
    }

    @RequestMapping("getRelationData")
    @ResponseBody
    public Map<String,Object> getRelationData() throws Exception{
        PageData pd = getPageData();
        List<PageData> reList = roleService.getReList(pd);//已关联菜单
        List<PageData> unList = roleService.getUnList(pd);//未关联菜单
        Map<String,Object> result = new HashMap<>();
        result.put("reList",reList);
        result.put("unList",unList);
        return result;
    }

    /**
     * 关联岗位菜单
     * @return
     * @throws Exception
     */
    @RequestMapping("relation")
    @ResponseBody
    public Map<String,Object> relation() throws Exception{
        PageData pd = getPageData();
        String roleId  = pd.getString("roleId");
        String ids = pd.getString("ids");
        String[] idsArr = ids.split(",");
        List<PageData> list = new ArrayList<>();
        for(String id : idsArr){
            if(id != null && !"".equalsIgnoreCase(id)){
                PageData pageData = new PageData();
                pageData.addParam("uuid",UuidUtil.get32UUID());
                pageData.addParam("roleId",roleId);
                pageData.addParam("menuId",id);
                list.add(pageData);
            }
        }
        roleService.saveRoleMenu(list,roleId);
        return getResultMap(true,"");
    }


}
