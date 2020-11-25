package com.genertech.phm.dzs.model;

import java.util.List;

public class ModelInfo {

    public final static Integer SOURCE_CZ = 1;
    public final static Integer SOURCE_SH = 2;
    public final static Integer SOURCE_XJ = 3;

    private String modelCode;
    private String modelName;
    private String outputCode;
    private String ckId;
    private String faultDesc;
    private List<String> trains;
    private String equipType;
    private Integer source;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOutputCode() {
        return outputCode;
    }

    public void setOutputCode(String outputCode) {
        this.outputCode = outputCode;
    }

    public String getCkId() {
        return ckId;
    }

    public void setCkId(String ckId) {
        this.ckId = ckId;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public List<String> getTrains() {
        return trains;
    }

    public void setTrains(List<String> trains) {
        this.trains = trains;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
