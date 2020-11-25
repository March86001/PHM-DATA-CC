package com.genertech.phm.log.service;

public interface ApiCallLogService {

	public void insertApiCallLog(String apiCode,String callResult,String callDesc);
}
