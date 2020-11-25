package com.genertech.phm.log.dao;

import java.util.List;
import com.genertech.phm.log.model.ApiDataCollect;

public interface ApiDataCollectMapper {
    int deleteByPrimaryKey(String apiCode);

    int insert(ApiDataCollect record);

    int insertSelective(ApiDataCollect record);

    ApiDataCollect selectByPrimaryKey(String apiCode);

    int updateByPrimaryKeySelective(ApiDataCollect record);

    int updateByPrimaryKey(ApiDataCollect record);

    List<ApiDataCollect> searchAll();
}