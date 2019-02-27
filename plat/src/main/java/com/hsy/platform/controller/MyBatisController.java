package com.hsy.platform.controller;

import com.hsy.platform.plugin.RefreshMapperCache;
import com.hsy.platform.utils.PropertiesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * mybatis 重载
 * author: husiyi
 */

@Controller
@RequestMapping("/mybatis/")
public class MyBatisController {

    @Resource
    RefreshMapperCache cache;

    @RequestMapping("reload")
    @ResponseBody
    public Map<String,Object> reload(){
        Map<String,Object> result = new HashMap<>();
        Boolean res = true;
        String msg = "重载成功";
        if( "true".equalsIgnoreCase(PropertiesUtils.getValueByKey("debugMode"))){
            try{
                cache.reload();
            }catch (Exception e){
                res = false;
                msg = e.getMessage();
            }
        }else{
            res = false;
            msg = "mybatis 重载只允许运行在debug模式";
        };
        result.put("res",res);
        result.put("msg",msg);
        return result;
    }
}
