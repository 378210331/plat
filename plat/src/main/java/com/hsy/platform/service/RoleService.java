package com.hsy.platform.service;

import com.hsy.platform.plugin.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends BaseService  {


    @Override
  public  String getMapperName() {
        return "RoleMapper";
    }

    /**
     * 删除
     * @param pd
     * @throws Exception
     */
    @Override
    public void delete(PageData pd) throws Exception {
        getDao().delete(getMapperName()+".deleteRoleMenu", pd);
        getDao().delete(getMapperName()+".delete", pd);
    }


    /**
     * 关联机构岗位
     * @throws Exception
     */
    public void saveRoleMenu(List<PageData> pdList, String roleId) throws Exception {
        PageData pd  = new PageData();
        pd.addParam("roleId",roleId);
        getDao().delete(getMapperName()+".deleteRoleMenu", pd);
        for(PageData p : pdList){
            getDao().save(getMapperName()+".saveRoleMenu",p);
        }
    }

    /**
     * 获取已关联的菜单列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> getReList(PageData pd) throws  Exception{
        return  getDao().queryPageDataListByKey(getMapperName()+".getReList",pd);
    }

    /**
     * 获取未关联的菜单列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> getUnList(PageData pd) throws  Exception{
        return  getDao().queryPageDataListByKey(getMapperName()+".getUnList",pd);
    }
}
