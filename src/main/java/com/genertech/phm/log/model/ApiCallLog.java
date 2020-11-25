package com.genertech.phm.log.model;

import java.util.Date;

public class ApiCallLog {
    private String id;	//主键

    private String callResult;	//调用结果：0失败，1成功

    private Date callTime;	//调用时间

    private String callDesc;	//调用结果说明

    private String apiCode;	//所属接口方法编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCallResult() {
        return callResult;
    }

    public void setCallResult(String callResult) {
        this.callResult = callResult == null ? null : callResult.trim();
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public String getCallDesc() {
        return callDesc;
    }

    public void setCallDesc(String callDesc) {
        this.callDesc = callDesc == null ? null : callDesc.trim();
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode == null ? null : apiCode.trim();
    }
}