package com.genertech.phm.core.identifier.service;


public interface IdProvider {
	
	/**
	 * 根据code获取generator实例
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	IdGenerator getGenerator(String code) throws Exception;
	
	/**
	 * 根据code获取下一个值
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	String nextVal(String code) throws Exception;

}
