package com.genertech.phm.core.identifier.dao;

import java.util.List;

import com.genertech.phm.core.identifier.model.IdConfig;

public interface IdConfigMapper {

    IdConfig selectByPrimaryKey(String configCode);

    IdConfig selectByPrimaryKeyForUpdate(String configCode);

    IdConfig selectValue(String configCode);

    int updateValue(IdConfig config);
    
    
    
    int insert(IdConfig record);

    List<IdConfig> selectAll();

    int updateByPrimaryKeySelective(IdConfig record);

    int deleteByPrimaryKey(String configCode);
}
