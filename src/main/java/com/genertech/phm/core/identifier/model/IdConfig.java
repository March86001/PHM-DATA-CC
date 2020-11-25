package com.genertech.phm.core.identifier.model;

import java.math.BigDecimal;

/***
 * 
 * @author 李翔龙
 * 
 */

public class IdConfig {
    /**
     * 编码<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : CONFIG_CODE
     */
    private String configCode;

    /**
     * 名称<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : CONFIG_NAME
     */
    private String configName;

    /**
     * 主表达式<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : EXPRESSION
     */
    private String expression;

    /**
     * 后缀（表达式）<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : SUFFIX
     */
    private String suffix;

    /**
     * 分隔符（表达式）<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : SEPARATOR
     */
    private String separator;

    /**
     * 序列当前值<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : SEQ_CURRENT_VALUE
     */
    private BigDecimal seqCurrentValue;

    /**
     * 序列开始值<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : SEQ_BEGIN_VALUE
     */
    private BigDecimal seqBeginValue;

    /**
     * 主表达式当前值<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : EXP_CURRENT_VALUE
     */
    private String expCurrentValue;

    /**
     * 是否从头计算<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : SUFFIX_REBEGIN
     */
    private String suffixRebegin;

    /**
     * 启用缓存<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : ENABLE_CACHE
     */
    private String enableCache;

    /**
     * 描述<br/>
     * 表 : DB_PHM_DEV.ID_CONFIG<br/>
     * 对应字段 : REMARK
     */
    private String remark;

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode == null ? null : configCode.trim();
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression == null ? null : expression.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator == null ? null : separator.trim();
    }

    public BigDecimal getSeqCurrentValue() {
        return seqCurrentValue;
    }

    public void setSeqCurrentValue(BigDecimal seqCurrentValue) {
        this.seqCurrentValue = seqCurrentValue;
    }

    public BigDecimal getSeqBeginValue() {
        return seqBeginValue;
    }

    public void setSeqBeginValue(BigDecimal seqBeginValue) {
        this.seqBeginValue = seqBeginValue;
    }

    public String getExpCurrentValue() {
        return expCurrentValue;
    }

    public void setExpCurrentValue(String expCurrentValue) {
        this.expCurrentValue = expCurrentValue == null ? null : expCurrentValue.trim();
    }

    public String getSuffixRebegin() {
        return suffixRebegin;
    }

    public void setSuffixRebegin(String suffixRebegin) {
        this.suffixRebegin = suffixRebegin == null ? null : suffixRebegin.trim();
    }

    public String getEnableCache() {
        return enableCache;
    }

    public void setEnableCache(String enableCache) {
        this.enableCache = enableCache == null ? null : enableCache.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
