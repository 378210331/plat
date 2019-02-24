package com.hsy.platform.service;

import com.hsy.platform.entity.TSysPos;
import com.hsy.platform.plugin.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosService extends BaseService   {

    @Override
    public String getMapperName() {
        return "GwMapper";
    }

    /**
     * 删除
     * @param pd
     * @throws Exception
     */
    @Override
    public void delete(PageData pd) throws Exception {
        getDao().delete(getMapperName()+".deleteGwMenu", pd);
        getDao().delete(getMapperName()+".delete", pd);
    }

    /**
     * 关联机构岗位
     * @throws Exception
     */
    public void saveGwMenu(List<PageData> pdList,String gwDm) throws Exception {
        PageData pd  = new PageData();
        pd.addParam("gwDm",gwDm);
        getDao().delete(getMapperName()+".deleteGwMenu", pd);
        for(PageData p : pdList){
            getDao().save(getMapperName()+".saveGwMenu",p);
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
     * 获取已关联的菜单列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<TSysPos> getUserPos(PageData pd) throws  Exception{
        return  getDao().getSqlSession().selectList(getMapperName()+".getUserGw",pd);
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
