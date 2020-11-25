package com.genertech.phm.core.identifier.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genertech.phm.core.identifier.dao.IdConfigMapper;
import com.genertech.phm.core.identifier.model.IdConfig;
import com.genertech.phm.core.identifier.service.IdGenerator;
import com.genertech.phm.core.identifier.service.IdProvider;

@Service
public class CommonIdProvider implements IdProvider
{

    @Autowired
    private IdConfigMapper  configMapper;

    private Map<String, IdGenerator> generatorMap = new HashMap<String, IdGenerator>();

    @Override
    public IdGenerator getGenerator(String code) throws Exception
    {
        if (generatorMap.containsKey(code))
            return generatorMap.get(code);
        else
        {
            IdConfig config = configMapper.selectByPrimaryKey(code);
            IdGenerator ig = new CommonIdGenerator(config, configMapper);
            if (config != null)
            {
                generatorMap.put(code, ig);
            }
            return ig;
        }

    }

    /*
     * REQUIRES_NEW表示独立事务，防止调用者长时间不提交导致主键获取的方法一直被阻塞
     * @see com.genertech.phm.core.identifier.service.IdProvider#nextVal(java.lang.String)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String nextVal(String code) throws Exception
    {
        return getGenerator(code).nextVal();
    }

    /**
     * @return the configMapper
     */
    public IdConfigMapper getConfigMapper()
    {
        return configMapper;
    }

    /**
     * @param configMapper
     *            the configMapper to set
     */
    public void setConfigMapper(IdConfigMapper configMapper)
    {
        this.configMapper = configMapper;
    }

}
