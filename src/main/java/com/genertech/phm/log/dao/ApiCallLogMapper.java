package com.genertech.phm.log.dao;

import com.genertech.phm.log.model.ApiCallLog;

public interface ApiCallLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApiCallLog record);

    int insertSelective(ApiCallLog record);

    ApiCallLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApiCallLog record);

    int updateByPrimaryKey(ApiCallLog record);
}