package com.genertech.phm.core.identifier.service;

import java.util.List;

import com.genertech.phm.core.identifier.model.IdConfig;

public interface IdConfigService {
    
    int deleteByPrimaryKey(String configCode);

    int insert(IdConfig record);

    List<IdConfig> selectAll();

    int updateByPrimaryKeySelective(IdConfig record);

}
