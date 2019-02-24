package com.hsy.platform.service;

import com.hsy.platform.plugin.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UserService")
public class UserService extends BaseService{

    public String getMapperName() {
        return "UserMapper";
    }

    public PageData getUserByLoginName(String s) throws Exception {
        PageData pd = getDao().queryPageDataByKey(getMapperName()+".getUserByLoginName",s);
        return pd;
    }


    @Override
    public void save(PageData pageData)throws Exception{
        String loginName = pageData.getString("loginName");
        String userId = pageData.getString("userId");
        PageData param = new PageData();
        pageData.addParam("userId",userId);
        List<PageData> dataList = this.getPageDataList(pageData);
        if(dataList.size()>0){
            throw  new Exception("已存在用户id为:"+userId+"的用户");
        }
        param.clear();
        pageData.addParam("loginName",loginName);
         dataList = this.getPageDataList(pageData);
        if(dataList.size()>0){
            throw  new Exception("已存在登录名为:"+loginName+"的用户");
        }
        super.save(pageData);
    }

}
