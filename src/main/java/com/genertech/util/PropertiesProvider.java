/**
 * project:xmlAnalysis
 * 
 * Copyright 2014 [genertech], Inc. All rights reserved.
 * * Website: http://www.gener-tech.com/
 * 
 */
package com.genertech.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


/**
 * 
 * @author zhuhonglin magiczhu
 * @email hlzhu1983(a)qq.com
 */
public class PropertiesProvider {

	static Properties props;
	
	private PropertiesProvider(){}
	
	private static void initProperties() {
		try {
			props = loadAllProperties("redis.properties");
		}catch(IOException e) {
			throw new RuntimeException("Load Properties error",e);
		}
	}
	
	public static Properties getProperties() {
		if(props == null)
			initProperties();
		return props;
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getProperties().getProperty(key, defaultValue);
	}

	public static String getProperty(String key) {
		return getProperties().getProperty(key);
	}
	
	public static Integer getIntegerProperty(String key) {
		return Integer.parseInt(getProperties().getProperty(key));
	}
	
	public static Boolean getBooleanProperty(String key,Boolean defaultValue) {
		String value = getProperties().getProperty(key);
		if (value == null) {
			return defaultValue;
		}else {
			try {
				Boolean result = Boolean.parseBoolean(value);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	public static Properties loadAllProperties(String resourceName) throws IOException {
		Properties properties = new Properties();
		Enumeration urls = PropertiesProvider.class.getClassLoader().getResources(resourceName);
		while (urls.hasMoreElements()) {
			URL url = (URL) urls.nextElement();
			InputStream is = null;
			try {
				URLConnection con = url.openConnection();
				con.setUseCaches(false);
				is = con.getInputStream();
				properties.load(is);
			}
			finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return properties;
	}
}
