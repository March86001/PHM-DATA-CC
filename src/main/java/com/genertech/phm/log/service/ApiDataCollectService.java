package com.genertech.phm.log.service;

import java.util.List;
import com.genertech.phm.log.model.ApiDataCollect;

public interface ApiDataCollectService {
	
	public void logToDB(String apiCode,String result,String desc);
	
	/**
	 * 最新接口日志更新
	 * @param apiDataCollect 接口日志对象
	 * @return
	 */
	public void updateApiDataCollect(String apiCode,String lastCallResult,String lastCallDesc);

	public List<ApiDataCollect> searchAll();
}
