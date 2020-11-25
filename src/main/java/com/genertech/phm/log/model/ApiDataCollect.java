package com.genertech.phm.log.model;

import java.util.Date;

public class ApiDataCollect {
    private String apiCode;	//接口方法编码

    private String apiName;	//接口方法名称

    private String apiFunction;	//接口方法功能

    private String apiSupplier;	//接口方法提供者

    private String dbTableName;	//数据库表名称

    private String paramDesc;	//参数说明

    private String lastCallResult;	//最近调用结果：0失败，1成功

    private Date lastCallTime;	//最近调用时间

    private String lastCallDesc;	//调用结果说明

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode == null ? null : apiCode.trim();
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public String getApiFunction() {
        return apiFunction;
    }

    public void setApiFunction(String apiFunction) {
        this.apiFunction = apiFunction == null ? null : apiFunction.trim();
    }

    public String getApiSupplier() {
        return apiSupplier;
    }

    public void setApiSupplier(String apiSupplier) {
        this.apiSupplier = apiSupplier == null ? null : apiSupplier.trim();
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName == null ? null : dbTableName.trim();
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc == null ? null : paramDesc.trim();
    }

    public String getLastCallResult() {
        return lastCallResult;
    }

    public void setLastCallResult(String lastCallResult) {
        this.lastCallResult = lastCallResult == null ? null : lastCallResult.trim();
    }

    public Date getLastCallTime() {
        return lastCallTime;
    }

    public void setLastCallTime(Date lastCallTime) {
        this.lastCallTime = lastCallTime;
    }

    public String getLastCallDesc() {
        return lastCallDesc;
    }

    public void setLastCallDesc(String lastCallDesc) {
        this.lastCallDesc = lastCallDesc == null ? null : lastCallDesc.trim();
    }
}