package com.genertech.phm.core.redis;

import java.io.IOException;
import java.util.Properties;

import com.genertech.phm.core.redis.util.RedisDML;
import com.genertech.phm.utils.PropsUtil;


public class RedisUtil {

	public static  final RedisDML basicTC_redis=new RedisDML(getPropsValue("redis.ip"),6379);

	private static Properties props = null;
	
	public static Properties getProps(){
		if(props == null){
			props = new Properties();
			try {
				props.load(PropsUtil.class.getResourceAsStream("/redis.properties"));
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
}
