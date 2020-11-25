/**
 * Copyright(C) 2015-2017 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2015-2017 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.7.0_67
 * <p>
 * 版本: 1.0版 文件名：com.genertech.phm.core.model.ResultModel.java
 * <p>
 * 作者: 郑阳文
 * <p>
 * 创建时间: 2017年3月13日下午4:07:55
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：郑阳文
 * <p>
 * 修改时间：2017年3月13日下午4:07:55
 * <p>
 */
package com.genertech.phm.core.model;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.genertech.phm.utils.GenertechBeanUtils;

public class ResultModel {
    // 请求状态
    private String    status;
    // 请求提示消息
    private String    msg;
    // 请求接口类型
    private String    type;
    // 开始时间
    private String    startTime;
    // 结束时间
    private String    endTime;
    // 记录条数
    private Integer   count;
    // 具体接口数据
    private JSONArray data;
    // 主表
    private JSONArray dataMain;
    // 子表
    private JSONArray dataSub;
    // 轮对尺寸数据
    private JSONArray dataSize;
    // 探伤数据
    private JSONArray dataExplorscar;
    // 轮对擦伤数据
    private JSONArray dataScar;
    // 主导科技受电弓检测设备受电弓级数据
    private JSONArray dataPanto;
    // 主导科技受电弓检测设备滑板数据
    private JSONArray dataSkateborad;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public Integer getCount() {
        return count == null ? data == null ? 0 : data.size() : count;
    }
    
    public Integer getDataMainCount(){
    	return count == null ? dataMain == null ? 0 : dataMain.size() : count;
    }
    
    public Integer getDataSubCount(){
    	return count == null ? dataSub == null ? 0 : dataSub.size() : count;
    }
    
    public Integer getDataSizeCount(){
    	return count == null ? dataSize == null ? 0 : dataSize.size() : count;
    }
    
    public Integer getDataExplorscarCount(){
    	return count == null ? dataExplorscar == null ? 0 : dataExplorscar.size() : count;
    }
    
    public Integer getDataScarCount(){
    	return count == null ? dataScar == null ? 0 : dataScar.size() : count;
    }
    
    public Integer getDataPantoCount(){
    	return count == null ? dataPanto == null ? 0 : dataPanto.size() : count;
    }
    
    public Integer getDataSkateboradCount(){
    	return count == null ? dataSkateborad == null ? 0 : dataSkateborad.size() : count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public JSONArray getDataMain() {
		return dataMain;
	}

	public void setDataMain(JSONArray dataMain) {
		this.dataMain = dataMain;
	}

	public JSONArray getDataSub() {
		return dataSub;
	}

	public void setDataSub(JSONArray dataSub) {
		this.dataSub = dataSub;
	}

	public JSONArray getDataSize() {
		return dataSize;
	}

	public void setDataSize(JSONArray dataSize) {
		this.dataSize = dataSize;
	}

	public JSONArray getDataExplorscar() {
		return dataExplorscar;
	}

	public void setDataExplorscar(JSONArray dataExplorscar) {
		this.dataExplorscar = dataExplorscar;
	}

	public JSONArray getDataScar() {
		return dataScar;
	}

	public void setDataScar(JSONArray dataScar) {
		this.dataScar = dataScar;
	}

	public JSONArray getDataPanto() {
		return dataPanto;
	}

	public void setDataPanto(JSONArray dataPanto) {
		this.dataPanto = dataPanto;
	}

	public JSONArray getDataSkateborad() {
		return dataSkateborad;
	}

	public void setDataSkateborad(JSONArray dataSkateborad) {
		this.dataSkateborad = dataSkateborad;
	}

	public <T> List<T> getDataList(Class<T> clazz) throws Exception {
        return GenertechBeanUtils.mapsToEntitys(getData(), clazz);
    }
	
	public <T> List<T> getDataMainList(Class<T> clazz) throws Exception {
        return GenertechBeanUtils.mapsToEntitys(getDataMain(), clazz);
    }
	
	public <T> List<T> getDataSubList(Class<T> clazz) throws Exception {
        return GenertechBeanUtils.mapsToEntitys(getDataSub(), clazz);
    }
	
	public <T> List<T> getDataSizeList(Class<T> clazz) throws Exception {
        return GenertechBeanUtils.mapsToEntitys(getDataSize(), clazz);
    }
	
	public <T> List<T> getDataExplorscarList(Class<T> clazz) throws Exception {
        return GenertechBeanUtils.mapsToEntitys(getDataExplorscar(), clazz);
    }
	
	public <T> List<T> getDataScarList(Class<T> clazz) throws Exception {
        return GenertechBeanUtils.mapsToEntitys(getDataScar(), clazz);
    }
	
	public <T> List<T> getDataPantoList(Class<T> clazz) throws Exception {
        return GenertechBeanUtils.mapsToEntitys(getDataPanto(), clazz);
    }
	
	public <T> List<T> getDataSkateboradList(Class<T> clazz) throws Exception {
        return GenertechBeanUtils.mapsToEntitys(getDataSkateborad(), clazz);
    }
}
