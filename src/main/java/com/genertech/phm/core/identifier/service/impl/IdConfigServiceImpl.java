package com.genertech.phm.core.identifier.service.impl;

import java.util.List;
/***
 * 
 * @author lxl
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genertech.phm.core.identifier.dao.IdConfigMapper;
import com.genertech.phm.core.identifier.model.IdConfig;
import com.genertech.phm.core.identifier.service.IdConfigService;

@Service
public class IdConfigServiceImpl implements IdConfigService {

    @Autowired
    private IdConfigMapper idConfigMapper;

    @Override
    public List<IdConfig> selectAll() {
        return idConfigMapper.selectAll();
    }

    @Override
    public int insert(IdConfig record) {
        return idConfigMapper.insert(record);
    }

    @Override
    public int deleteByPrimaryKey(String configCode) {
        return idConfigMapper.deleteByPrimaryKey(configCode);
    }

    @Override
    public int updateByPrimaryKeySelective(IdConfig record) {
        return idConfigMapper.updateByPrimaryKeySelective(record);
    }

}
