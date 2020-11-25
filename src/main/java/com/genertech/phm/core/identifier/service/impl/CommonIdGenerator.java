package com.genertech.phm.core.identifier.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.genertech.phm.core.el.CommonELResolver;
import com.genertech.phm.core.identifier.dao.IdConfigMapper;
import com.genertech.phm.core.identifier.model.IdConfig;
import com.genertech.phm.core.identifier.service.IdGenerator;
import com.genertech.phm.core.identifier.util.Identities;

public class CommonIdGenerator implements IdGenerator {

    private IdConfigMapper configMapper;

    private IdConfig       config;

    public CommonIdGenerator(IdConfig config, IdConfigMapper configMapper) {
        this.config = config;
        this.configMapper = configMapper;
    }

    /**
     * 获取下一个值,若无配置,则返回uuid 同步方法,对同一个配置必须是单例
     */
    @Override
    public String nextVal() {
        if (config == null) return Identities.uuid();
        // 暂时用selectByPrimaryKey
        // 使用行级锁解决同步问题
        IdConfig ic = configMapper.selectByPrimaryKey(config.getConfigCode());
        CommonELResolver elResolver = CommonELResolver.getDefaultWebResolver();
        // elResolver.addVariable("user", Securitys.getUser());
        BigDecimal seqNextValue = ic.getSeqCurrentValue();
        if (seqNextValue == null) seqNextValue = ic.getSeqBeginValue();
        else seqNextValue = seqNextValue.add(new BigDecimal(1));
        elResolver.addVariable("seqNextValue", seqNextValue);

        String expNextValue = "";
        if (!StringUtils.isEmpty(ic.getExpression())) expNextValue = elResolver.evaluate(ic.getExpression());

        // 一旦expression计算的值不一致seq的值从头计算,满足特定需求
        if ("1".equals(ic.getSuffixRebegin())
                && !StringUtils.isEmpty(expNextValue)
                && !StringUtils.isEmpty(ic.getExpCurrentValue())
                && !ic.getExpCurrentValue().equals(expNextValue)) {
            seqNextValue = ic.getSeqBeginValue();
        }

        String sepNextValue = "";
        String suffixNextValue = "";
        elResolver.addVariable("seqNextValue", seqNextValue);
        if (!StringUtils.isEmpty(ic.getSeparator())) sepNextValue = elResolver.evaluate(ic.getSeparator());
        if (!StringUtils.isEmpty(ic.getSuffix())) suffixNextValue = elResolver.evaluate(ic.getSuffix());
        if (!StringUtils.isEmpty(ic.getExpression())) expNextValue = elResolver.evaluate(ic.getExpression());
        // 设置新的主表达式计算结果
        ic.setExpCurrentValue(expNextValue);
        ic.setSeqCurrentValue(seqNextValue);
        configMapper.updateValue(ic);
        return expNextValue + sepNextValue + suffixNextValue;
    }

    public IdConfig getConfig() {
        return config;
    }

    public void setConfig(IdConfig config) {
        this.config = config;
    }

    /**
     * @return the configMapper
     */
    public IdConfigMapper getConfigMapper() {
        return configMapper;
    }

    /**
     * @param configMapper
     *            the configMapper to set
     */
    public void setConfigMapper(IdConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

}
