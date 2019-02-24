package com.hsy.platform.service;

import com.hsy.platform.plugin.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemService extends  BaseService {


    @Override
    public String getMapperName() {
        return "SystemMapper";
    }

    public  List<PageData> getMenuBySystem (PageData pd) throws Exception {
        return getDao().queryPageDataListByKey(getMapperName()+".getMenuBySystem",pd);
    }
}
