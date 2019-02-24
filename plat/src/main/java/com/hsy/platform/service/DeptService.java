package com.hsy.platform.service;

import com.hsy.platform.plugin.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService extends BaseService  {


    @Override
    public String getMapperName() {
        return "DeptMapper";
    }

    /**
     * 获取税务机关tree
     * @param pd
     * @return
     */
    public List<PageData> getDeptTree(PageData pd) throws Exception {
        return getDao().queryPageDataListByKey(getMapperName()+".getDeptTree",pd);
    }
}
