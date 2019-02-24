package com.hsy.platform.controller;

import com.hsy.platform.plugin.Page;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.PosService;
import com.hsy.platform.utils.LoginInfoUtils;
import com.hsy.platform.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pos/")
public class PosController extends  BaseController {

    Logger log = LoggerFactory.getLogger(PosController.class);

    @Resource
    PosService posService;

    @RequestMapping("init")
    public ModelAndView init(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("security/pos_list");
        return mv;
    }

    @RequestMapping("onEdit")
    public ModelAndView onEdit() throws Exception {
        ModelAndView mv = new ModelAndView("security/pos_edit");
        mv.addObject("pos_id",getRequest().getParameter("pos_id"));
        return mv;
    }

    @RequestMapping("list")
    @ResponseBody
    public Page list(Page page) throws Exception {
        page.setPd(this.getPageData());
        page =posService .listPage(page);
        return page ;
    }

    @RequestMapping("query")
    @ResponseBody
    public Map<String,Object> query() throws Exception {
        PageData pd = getPageData();
        List<PageData>  list =  posService.getPageDataList(pd);
        return this.getDataMap(true,list);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete() throws Exception {
        PageData pd = getPageData();
        posService.delete(pd);
        return getResultMap(true,DELETE_SUCCESS);
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String, Object> save() throws Exception {
        PageData pd = getPageData();
        pd.addParam("lrrDm", LoginInfoUtils.getUserId());
        pd.addParam("pos_id", UuidUtil.get32UUID());
        posService.save(pd);
        return getResultMap(true,SAVE_SUCCESS);
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update() throws Exception {
        PageData pd = getPageData();
        pd.addParam("xgrDm", LoginInfoUtils.getUserId());
        posService.update(pd);
        return getResultMap(true,UPDATE_SUCCESS);
    }

    @RequestMapping("onRelation")
    public ModelAndView onRelation(){
            ModelAndView mv = new ModelAndView("security/pos_relation");
            mv.addObject("pos_id",getRequest().getParameter("pos_id"));
            return mv;
    }

    @RequestMapping("getRelationData")
    @ResponseBody
    public Map<String,Object> getRelationData() throws Exception{
        PageData pd = getPageData();
        List<PageData> reList = posService.getReList(pd);//已关联菜单
        List<PageData> unList = posService.getUnList(pd);//未关联菜单
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
        String posId  = pd.getString("posId");
        String ids = pd.getString("ids");
        String[] idsArr = ids.split(",");
        List<PageData> list = new ArrayList<>();
        for(String id : idsArr){
            if(id != null && !"".equalsIgnoreCase(id)){
                PageData pageData = new PageData();
                pageData.addParam("uuid",UuidUtil.get32UUID());
                pageData.addParam("posId",posId);
                pageData.addParam("menuId",id);
                list.add(pageData);
            }
        }
        posService.saveGwMenu(list,posId);
        return getResultMap(true,"");
    }
}
