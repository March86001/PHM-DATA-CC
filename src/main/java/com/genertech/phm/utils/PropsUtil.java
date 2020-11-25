package com.genertech.phm.utils;

import java.io.IOException;
import java.util.Properties;

public class PropsUtil {

	public static final String EMIS_ASYNC_URL = "emis.async.url";
	public static final String EMIS_ASYNC_NAMESPACE = "emis.async.namespace";
	public static final String EMIS_ASYNC_USERNAME = "emis.async.username";
	public static final String EMIS_ASYNC_PASSWORD = "emis.async.password";
	public static final String EMIS_ASYNC_CLIENTID = "emis.async.clientId";

	public static final String GENER_SQLITE_URL = "gener.sqlite.url";
	public static final String GENER_SQLITEPARAM_URL = "gener.sqliteParam.url";
	


	private static Properties props = null;
	private PropsUtil(){}
	
	public static Properties getProps(){
		if(props == null){
			props = new Properties();
			try {
				props.load(PropsUtil.class.getResourceAsStream("/jdbc.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
	}
	
	public static String getPropsValue(String key){
		Properties p = getProps();
		return p.getProperty(key, "");
	}
	
	public static String getPropsValue(String key, String defaultVal){
		Properties p = getProps();
		return p.getProperty(key, defaultVal);
	}
	
}
