package com.hsy.platform.service;

import org.springframework.stereotype.Service;

@Service
public class FileService extends  BaseService{
    @Override
    public String getMapperName() {
        return "FileMapper";
    }
}
