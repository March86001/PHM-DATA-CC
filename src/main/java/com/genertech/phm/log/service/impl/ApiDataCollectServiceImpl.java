package com.genertech.phm.log.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genertech.phm.log.dao.ApiDataCollectMapper;
import com.genertech.phm.log.model.ApiDataCollect;
import com.genertech.phm.log.service.ApiCallLogService;
import com.genertech.phm.log.service.ApiDataCollectService;
import com.genertech.phm.utils.ExceptionsUtils;

@Service
public class ApiDataCollectServiceImpl implements ApiDataCollectService{
	
	private static final Log logger = LogFactory.getLog(ApiDataCollectServiceImpl.class);
	
	@Autowired
	private ApiDataCollectMapper apiDataCollectMapper;
	@Autowired
	private ApiCallLogService apiCallLogService;
	
	@Override
	public void logToDB(String apiCode, String result, String desc) {
		if(desc.length()>=3000){
			desc = desc.substring(0,3000);
		}
		this.updateApiDataCollect(apiCode, result, desc);
		this.apiCallLogService.insertApiCallLog(apiCode, result, desc);
	}

	/**
	 * 更新最新接口日志
	 * @param apiCode 接口方法编码，主键
	 * @param lastCallResult 最新调用结果：0失败，1成功
	 * @param lastCallTime 最近调用时间
	 * @param lastCallDesc 调用结果说明
	 */
	@Override
	public void updateApiDataCollect(String apiCode,String lastCallResult,String lastCallDesc) {
		ApiDataCollect apiDataCollect = new ApiDataCollect();
		if(apiCode!=null&&!"".equals(apiCode)){
			apiDataCollect.setApiCode(apiCode);
		}
		if(lastCallResult!=null&&!"".equals(lastCallResult)){
			apiDataCollect.setLastCallResult(lastCallResult);
		}
		if(lastCallDesc!=null&&!"".equals(lastCallDesc)){
			apiDataCollect.setLastCallDesc(lastCallDesc);
		}
		
		try {
			int update = apiDataCollectMapper.updateByPrimaryKeySelective(apiDataCollect);
			if(update>0){
				logger.info("最新接口日志更新成功");
			}else{
				int insert = apiDataCollectMapper.insertSelective(apiDataCollect);
				if (insert > 0 ){
					logger.info("最新接口日志更新成功");
				} else {
					logger.info("最新接口日志更新失败");
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionsUtils.getStackTraceAsString(e));
		}
	}

	@Override
	public List<ApiDataCollect> searchAll() {
		return apiDataCollectMapper.searchAll();
	}
}
