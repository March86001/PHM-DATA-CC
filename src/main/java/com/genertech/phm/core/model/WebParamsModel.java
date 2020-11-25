/**
 * Copyright(C) 2015-2017 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2015-2017 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.7.0_67
 * <p>
 * 版本: 1.0版 文件名：com.genertech.phm.core.model.WebParamsModel.java
 * <p>
 * 作者: 郑阳文
 * <p>
 * 创建时间: 2017年4月11日上午10:54:09
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：郑阳文
 * <p>
 * 修改时间：2017年4月11日上午10:54:09
 * <p>
 */
package com.genertech.phm.core.model;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.encoding.XMLType;

public class WebParamsModel {

    private String        name;
    private Object        value;
    private QName         dataType;
    private ParameterMode paramMode;

    public WebParamsModel() {
    }
    
    public WebParamsModel(String name, Object value) {
        this.name = name;
        this.value = value;
        this.dataType = XMLType.XSD_STRING;
        this.paramMode = ParameterMode.IN;
    }
    
    public WebParamsModel(String name, Object value, QName dataType) {
        this.name = name;
        this.value = value;
        this.dataType = dataType;
        this.paramMode = ParameterMode.IN;
    }
    
    public WebParamsModel(String name, Object value, QName dataType, ParameterMode paramMode) {
        this.name = name;
        this.value = value;
        this.dataType = dataType;
        this.paramMode = paramMode;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public QName getDataType() {
        return dataType;
    }

    public void setDataType(QName dataType) {
        this.dataType = dataType;
    }

    public ParameterMode getParamMode() {
        return paramMode;
    }

    public void setParamMode(ParameterMode paramMode) {
        this.paramMode = paramMode;
    }

    @Override
    public String toString() {
        String typeStr = this.dataType.toString().replace("{" + XMLType.URI_DEFAULT_SCHEMA_XSD + "}", "");
        return this.name + "=" + this.value + "[" + typeStr + "]";
    }

}
