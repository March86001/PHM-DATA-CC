package com.genertech.phm.log.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genertech.phm.log.dao.ApiCallLogMapper;
import com.genertech.phm.log.model.ApiCallLog;
import com.genertech.phm.log.service.ApiCallLogService;
import com.genertech.phm.utils.ExceptionsUtils;

@Service
public class ApiCAllLogServiceImpl implements ApiCallLogService{
	
	private static final Log logger = LogFactory.getLog(ApiCAllLogServiceImpl.class);

	@Autowired
	private ApiCallLogMapper apiCallLogMapper;
	
	@Override
	public void insertApiCallLog(String apiCode,String callResult,String callDesc) {
		ApiCallLog apiCallLog = new ApiCallLog();
		if(apiCode!=null&&!"".equals(apiCode)){
			apiCallLog.setApiCode(apiCode);
		}
		if(callResult!=null&&!"".equals(callResult)){
			apiCallLog.setCallResult(callResult);
		}
		if(callDesc!=null&&!"".equals(callDesc)){
			apiCallLog.setCallDesc(callDesc);
		}
		try {
			int insert = apiCallLogMapper.insertSelective(apiCallLog);
			if(insert>0){
				logger.info("接口日志历史记录新增成功");
			}else{
				logger.info("接口日志历史记录新增失败");
			}
		} catch (Exception e) {
			logger.error(ExceptionsUtils.getStackTraceAsString(e));
		}
	}

}
