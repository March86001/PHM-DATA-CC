package com.genertech.phm.core.redis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class RedisDML {
	private  static Logger log=Logger.getLogger(RedisDML.class);
	private   String REDIS_IP;
	private   Integer REDIS_PORT; 
	//内部类  
	//private static RedisDML instance = null;  

	public RedisDML(String redis_ip,Integer redis_port) {  
		this.REDIS_IP=redis_ip;
		this.REDIS_PORT=redis_port;
	}  

	/*private static synchronized void syncInit(String redis_ip,Integer redis_port) {  
			if (instance == null) {  
				instance = new RedisDML(redis_ip,redis_port);  
			}  
		}  

		public static RedisDML getInstance(String redis_ip,Integer redis_port) {  
			if (instance == null) {  
				syncInit(redis_ip,redis_port);  
			}  
			return instance;  
		}  

	 */		


	public String getREDIS_IP() {
		return REDIS_IP;
	}

	public void setREDIS_IP(String rEDIS_IP) {
		REDIS_IP = rEDIS_IP;
	}

	public Integer getREDIS_PORT() {
		return REDIS_PORT;
	}

	public void setREDIS_PORT(Integer rEDIS_PORT) {
		REDIS_PORT = rEDIS_PORT;
	}




	//参数的存放，默认存活2天=32*60*60秒
	private   int expire_time = 115200;
	//过期数据存活日期:默认2天
	public   int expire_day = -2;


	public   Jedis getJedis() {
		return JedisUtil.getJedis(REDIS_IP, REDIS_PORT);
	}

	public   void closeJedis(Jedis jedis) {
		JedisUtil.closeJedis(jedis, REDIS_IP, REDIS_PORT);
	}

	/**
	 * 清除redis Map key
	 * @param redis
	 */
	public  void removeJedisCache(Jedis redis){
		JedisUtil.removeJedisCache(redis, REDIS_IP, REDIS_PORT);
	}

	public   void hsetObj(String key,Map<String, String> resultMap){
		Jedis jedis = null;
		if (resultMap != null && resultMap.size() > 0) {
			try{
				jedis = getJedis();
				jedis.hmset(key, resultMap);
				jedis.expire(key, expire_time);
			}catch(Exception e){
				log.error("redis执行异常!!!",e);
				removeJedisCache(jedis);
			}finally{
				closeJedis(jedis);
			}
		}
	}

	/**
	 * 用于永久存放基础数据
	 * @param key
	 * @param resultMap
	 */
	public   void hBatchMap(Map<String,Map<String, String>> resultMap){
		Jedis jedis = null;
		if (resultMap != null && resultMap.size() > 0) {
			try{
				jedis = getJedis();
				Pipeline p = jedis.pipelined();
				for(Entry<String, Map<String,String>> entry:resultMap.entrySet()){
					String key=entry.getKey();
					Map<String,String> hash=entry.getValue();
					p.hmset(key, hash);
				}
				p.sync();
			}catch(Exception e){
				log.error("redis执行异常!!!",e);
				removeJedisCache(jedis);
			}finally{
				closeJedis(jedis);
			}
		}
	}
	
	
	/**
	 * 用于永久存放基础数据
	 * @param key
	 * @param resultMap
	 */
	public   void hPermanentSetObj(String key,Map<String, String> resultMap){
		Jedis jedis = null;
		if (resultMap != null && resultMap.size() > 0) {
			try{
				jedis = getJedis();
				jedis.hmset(key, resultMap);
			}catch(Exception e){
				log.error("redis执行异常!!!",e);
				removeJedisCache(jedis);
			}finally{
				closeJedis(jedis);
			}
		}
	}

	public   List<Map<String, String>> getHashByKeyPattern(String pattern){
		Jedis jedis = null;
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		try{
			jedis = getJedis();
			Set<String> keys = jedis.keys(pattern);
			for (String key : keys) {
				Map<String, String> value = jedis.hgetAll(key);
				result.add(value);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   Map<String,Map<String, String>> getMapByKeyPattern(String pattern){
		Jedis jedis = null;
		Map<String,Map<String, String>> result = new HashMap<String,Map<String,String>>();
		try{
			jedis = getJedis();
			Set<String> keys = jedis.keys(pattern);
			for (String key : keys) {
				Map<String, String> value = jedis.hgetAll(key);
				result.put(key, value);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}
	
	

	public   Set<String> getKeysByPattern(String keypattern){
		Jedis jedis = null;
		Set<String> keys = new HashSet<String>();
		try{
			jedis = getJedis();
			keys = jedis.keys(keypattern);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return keys;
	}


	public   List<String> getHashByKeyPatternAndField(String pattern,String field){
		Jedis jedis = null;
		List<String> result = new ArrayList<String>();
		Set<String> keys = new HashSet<String>();
		try{
			jedis = getJedis();
			keys = jedis.keys(pattern);
			for (String key : keys) {
				Map<String, String> value = jedis.hgetAll(key);
				String f = value.get(field);
				if (f != null) {
					result.add(f);
				}
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   List<String> getListByKeyPattern(String key,String pattern){
		Jedis jedis = null;
		List<String> list = new ArrayList<String>();
		try{
			jedis = getJedis();
			list = jedis.lrange(key, 0, -1);
			Iterator<String> sListIterator = list.iterator();  
			while(sListIterator.hasNext()){  
				String e = sListIterator.next();
				if (!e.contains(pattern)) {
					sListIterator.remove(); 
				}
			}  
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return list;
	}

	public   void zsetObj(String key,String score,Object obj,Integer seconds,Long size){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.zadd(key, Double.parseDouble(score), JSON.toJSONString(obj));
			if (seconds != null) {
				jedis.expire(key, seconds);
			} else {
				jedis.expire(key, expire_time);
			}
			if (size != null && jedis.zcard(key) > (size + 1)) {
				jedis.zremrangeByRank(key, size + 1, size + 2);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	public   void zsetBytes(String key,String score,byte[] bytes,Integer seconds,Long size){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.zadd(key.getBytes(), Double.parseDouble(score), bytes);
			if (seconds != null) {
				jedis.expire(key, seconds);
			} else {
				jedis.expire(key, expire_time);
			}
			if (size != null && jedis.zcard(key) > (size + 1)) {
				jedis.zremrangeByRank(key, size + 1, size + 2);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	public   void zsetStr(String key,String score,String str){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.zadd(key, Double.parseDouble(score), str);
			jedis.expire(key, expire_time);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	public   void zsetStrValue(String key,String score,String str){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.zadd(key, Double.parseDouble(score), str);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	public   List<String> blpop(int timeout,String key){
		Jedis jedis = null;
		List<String> result = new ArrayList<String>();
		try{
			jedis = getJedis();
			result = jedis.blpop(timeout, key);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   List<byte[]> blpopBytes(int timeout,String key){
		Jedis jedis = null;
		List<byte[]> result = new ArrayList<byte[]>();
		try{
			jedis = getJedis();
			result = jedis.blpop(timeout, key.getBytes());
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   String lpop(String key){
		Jedis jedis = null;
		String result = null;
		try{
			jedis = getJedis();
			result = jedis.lpop(key);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   void lpush(String key,String value,Long size,boolean isHead){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			if (isHead) {
				jedis.lpush(key, value);
			}else {
				jedis.rpush(key, value);
			}
			if (size != null && jedis.llen(key) > (size + 1)) {
				jedis.ltrim(key, 0, size);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	public   void lBatchPush(String key,List<String> list,boolean isHead){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			if(list!=null&&list.size()>0){
				String[] values = list.toArray(new String[list.size()]); 
				if (isHead) {
					jedis.lpush(key, values);
				}else {
					jedis.rpush(key, values);
				}
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	

	public   void lpush(String key,byte[] value,Long size,boolean isHead){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			if (isHead) {
				jedis.lpush(key.getBytes(), value);
			}else {
				jedis.rpush(key.getBytes(), value);
			}
			if (size != null && jedis.llen(key) > (size + 1)) {
				jedis.ltrim(key, 0, size);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	public   Set<String> zPop(String key,boolean asc,Long start,Long end){
		Jedis jedis = null;
		Set<String> s = new HashSet<String>();
		try{
			jedis = getJedis();
			if (asc) {
				s = jedis.zrange(key, start, end);
			}else {
				s = jedis.zrevrange(key, start, end);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return s;
	}

	public   Set<byte[]> zPopBytes(String key,boolean asc,Integer start,Integer end){
		Jedis jedis = null;
		Set<byte[]> s = new HashSet<byte[]>();
		try{
			jedis = getJedis();
			if (asc) {
				s = jedis.zrange(key.getBytes(), start, end);
			}else {
				s = jedis.zrevrange(key.getBytes(), start, end);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return s;
	}

	public   List<String> lRange(String key,Long start,Long end){
		Jedis jedis = null;
		List<String> s = new ArrayList<String>();
		try{
			jedis = getJedis();
			s = jedis.lrange(key, start, end);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return s;
	}

	public   void lRem(String key,String value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.lrem(key, 0, value);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	public   String getStrValue(String key){
		Jedis jedis = null;
		String value = null;
		try{
			jedis = getJedis();
			value = jedis.get(key);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return value;
	}

	public   List<String> getStrValueByPattern(String pattern){
		Jedis jedis = null;
		List<String> result = new ArrayList<String>();
		try{
			jedis = getJedis();
			Set<String> keys = jedis.keys(pattern);
			for (String key : keys) {
				result.add(jedis.get(key));
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}
	
	
	public   Map<String,String> getMapStrValueByPattern(String pattern){
		Jedis jedis = null;
		Map<String,String> result = new HashMap<String,String>();
		try{
			jedis = getJedis();
			Set<String> keys = jedis.keys(pattern);
			for (String key : keys) {
				result.put(key, jedis.get(key));
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   void setStrValue(String key,String value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.set(key,value);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	public   void addSetValue(String key,String value,Long expiretime){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.sadd(key,value);
			if(expiretime==null){
				jedis.expire(key, expire_time);
			}else{
				jedis.expire(key, expire_time);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	public   void setByteValue(String key,byte[] value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.set(key.getBytes(),value);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	public   Map<String,byte[]> getMapByteValue(String pattern){
		Jedis jedis = null;
		Map<String,byte[]> result = new HashMap<String,byte[]>();
		try{
			jedis = getJedis();
			Set<String> keys = jedis.keys(pattern);
			for (String key : keys) {
				result.put(key, jedis.get(key.getBytes()));
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}
	
	
	
	public   void setByteValue(String key,byte[] value,int time){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.set(key.getBytes(),value);
			jedis.expire(key, time);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}


	public   byte[] getByteValue(String key){
		Jedis jedis = null;
		byte[] result = null;
		try{
			jedis = getJedis();
			result = jedis.get(key.getBytes());
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   void hSet(String key,String field,String value){
		Jedis jedis = null;
		try{
			jedis = getJedis(); 
			jedis.hset(key,field,value);
			jedis.expire(key, expire_time);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	/**
	 * 用来永久存放基础数据
	 * @param key
	 * @param field
	 * @param value
	 */
	public   void hPermanentSet(String key,String field,String value){
		Jedis jedis = null;
		try{
			jedis = getJedis(); 
			jedis.hset(key,field,value);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 用来永久存放基础数据
	 * @param key
	 * @param field
	 * @param value
	 */
	public   void hPermanentSet(String key,String field,String value,Integer time){
		Jedis jedis = null;
		try{
			jedis = getJedis(); 
			jedis.hset(key,field,value);
			if(time==null){
				jedis.expire(key, expire_time);
			}else{
				jedis.expire(key, time);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	public   String hGet(String key,String field){
		Jedis jedis = null;
		String result = null;
		try{
			jedis = getJedis(); 
			result = jedis.hget(key,field);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   Map<String, String> hGetAll(String key){
		Jedis jedis = null;
		Map<String, String> result = new HashMap<String,String>();
		try{
			jedis = getJedis(); 
			result = jedis.hgetAll(key);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}
	
	public  byte[] hGetByte(byte[] key,byte[] field){
		Jedis jedis = null;
		byte[] result = null;
		try{
			jedis = getJedis(); 
			result = jedis.hget(key,field);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   List<String> getZsetByKeyPatternAndScore(String pattern,String min,String max){
		Jedis jedis = null;
		List<String> result = new ArrayList<String>();
		Set<String> keys = new HashSet<String>();
		try{
			jedis = getJedis();
			keys = jedis.keys(pattern);
			for (String key : keys) {
				Set<String> s = jedis.zrangeByScore(key, min, max);
				result.addAll(s);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}
	
	public   Map<String,Set<String>> getMapZsetByKeyPatternAndScore(String pattern,String min,String max){
		Jedis jedis = null;
		Map<String,Set<String>> result = new HashMap<String,Set<String>>();
		Set<String> keys = new HashSet<String>();
		try{
			jedis = getJedis();
			keys = jedis.keys(pattern);
			for (String key : keys) {
				Set<String> s = jedis.zrangeByScore(key, min, max);
				result.put(key, s);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   Set<byte[]> getZsetByKeyAndScore(String key,String min,String max){
		Jedis jedis = null;
		Set<byte[]> result = new HashSet<byte[]>();
		try{
			jedis = getJedis();
			result = jedis.zrangeByScore(key.getBytes(), min.getBytes(), max.getBytes());
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   Set<byte[]> getZsetByKeyAndScore(String key,double min,double max){
		Jedis jedis = null;
		Set<byte[]> result = new HashSet<byte[]>();
		try{
			jedis = getJedis();
			result = jedis.zrangeByScore(key.getBytes(), min, max);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   Set<String> getZsetStrByKeyAndScore(String key,String min,String max){
		Jedis jedis = null;
		Set<String> result = new HashSet<String>();
		try{
			jedis = getJedis();
			result = jedis.zrangeByScore(key, min, max);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}
	
	public   Set<String> zrangeByScore(String key,double min,double max){
		Jedis jedis = null;
		Set<String> result = new HashSet<String>();
		try{
			jedis = getJedis();
			result = jedis.zrangeByScore(key, min, max);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   Set<byte[]> getZsetByteByKeyAndScore(String key,String min,String max){
		Jedis jedis = null;
		Set<byte[]> result = new HashSet<byte[]>();
		try{
			jedis = getJedis();
			result = jedis.zrangeByScore(key.getBytes(), min.getBytes(), max.getBytes());
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public  String getZsetByKeyAndScore(String key,double score){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			Set<String> set = jedis.zrangeByScore(key, score, score);
			if(set!=null&&set.size()>0){
				for(String str:set){
					return str;
				}
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return null;
	}

	public   void updateZsetMemeber(String key,String v1,String v2,double score){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.zrem(key, v1);
			jedis.zadd(key, score, v2);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	public   long lLen(String key){
		Jedis jedis = null;
		long size = 0;
		try{
			jedis = getJedis();
			size = jedis.llen(key);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return size;
	}

	public   long sadd(String setName, String... sValue){
		Jedis jedis = null;
		long size = 0;
		try{
			jedis = getJedis();
			size = jedis.sadd(setName, sValue);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return size;
	}
	/**
	 * sadd的方法上加上过期时间
	 * @param setName
	 * @param sValue 
	 * @param iExpireTimeSecs  过期时间 (秒)
	 * @return
	 */
	public   long sadd(String setName, String sValue, Integer iExpireTimeSecs){
		Jedis jedis = null;
		long size = 0;
		try{
			jedis = getJedis();
			size = jedis.sadd(setName, sValue);
			if(iExpireTimeSecs == null){
				iExpireTimeSecs = expire_time;
			}
			jedis.expire(setName, iExpireTimeSecs);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return size;
	}

	public   Set<String> smembers(String setName){
		Jedis jedis = null;
		Set<String>  members = new HashSet<String>();
		try{
			jedis = getJedis();
			members = jedis.smembers(setName);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return members;
	}

	public   boolean sismember(String setName, String sValue){
		Jedis jedis = null;
		boolean ismember = false;
		try{
			jedis = getJedis();
			ismember = jedis.sismember(setName, sValue);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return ismember;
	}

	/**
	 * 将List中的第一条记录移出List
	 * @param byte[] key
	 * @return 移出的记录
	 * */
	public   byte[] lpop(byte[] key) {
		Jedis jedis = null;
		byte[] value = null;
		try{
			jedis = getJedis();
			value = jedis.lpop(key);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return value;
	}

	/**
	 * 将List中的第一条记录移出List
	 * @param byte[] key
	 * @return 移出的记录
	 * */
	public   byte[] lspop(String key) {
		Jedis jedis = null;
		byte[] value = null;
		try{
			jedis = getJedis();
			byte[] bKey = key.getBytes();
			value = jedis.lpop(bKey);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return value;
	}

	/**
	 * 向List中追加记录
	 * @param byte[] key
	 * @param byte[] value
	 * @return 记录总数
	 * */
	public long lpush(byte[] key, byte[] value) {
		Jedis jedis = null;
		long count = 0;
		try{
			jedis = getJedis();
			count = jedis.lpush(key, value);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return count;
	}

	public long lspush(String key, byte[] value) {
		Jedis jedis = null;
		long count = 0;
		try{
			jedis = getJedis();
			byte[] bKey = key.getBytes();
			count = jedis.lpush(bKey, value);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return count;
	}

	public   boolean hexists(String key, String field) {
		Jedis jedis = null;
		boolean ifExists = false;
		try{	 
			jedis = getJedis();
			ifExists = jedis.hexists(key, field);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return ifExists;
	}

	
	
	public   void delByKey(String key){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.del(key);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	public   void hdel(String key,String field){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.hdel(key,field);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	/**
	 * 删除key中score从min到max之间的元素
	 * @param key
	 * @param min
	 * @param max
	 */
	public   void zremrangeByScore(String key,String min,String max){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.zremrangeByScore(key, min, max);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	/**
	 * 对set元素执行删除操作
	 * @param key
	 * @param members
	 */
	public   void srem(String key,String... members){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.srem(key, members);
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	/**
	 * 向list中批量插入数据
	 * @param key
	 * @param value
	 * @param size
	 * @param isHead
	 */
	public   void lpushBatch(String key,Set<String> values,Long size,boolean isHead){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			Pipeline p = jedis.pipelined();
			if(values != null && 0 < values.size()){
				for(String value : values){
					if (isHead) {
						p.lpush(key, value);
					}else {
						p.rpush(key, value);
					}
				}
			}
			p.sync();
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}

	
	public   List<Response<String>> rpopBatch(String key,int len){
		Jedis jedis = null;
		List<Response<String>> result = new ArrayList<Response<String>>();
		try{
			jedis = getJedis();
			Pipeline p = jedis.pipelined();
			for(int i = 0 ; i<len ; i++){
				Response<String> res = p.rpop(key);
				if(res != null){
					result.add(res);
				}else{
					break;
				}
			}
			p.sync();
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}

	public   List<Response<String>> lpopBatch(String key,int len){
		Jedis jedis = null;
		List<Response<String>> result = new ArrayList<Response<String>>();
		try{
			jedis = getJedis();
			Pipeline p = jedis.pipelined();
			for(int i = 0 ; i<len ; i++){
				Response<String> res = p.lpop(key);
				if(res != null){
					result.add(res);
				}else{
					break;
				}
			}
			p.sync();
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
		return result;
	}


	public   <T> void zsetBytesBatch(List<String[]> list,double score,Integer expire){
		Jedis jedis = null;
		try{
			if(expire == null){
				expire = expire_time;
			}
			if(list!=null&&list.size()>0){
				jedis = getJedis();
				Pipeline p = jedis.pipelined();
				for(String[] strs:list){
					String key =strs[0];
					String member=strs[1];
					p.zadd(key, score, member);
					p.expire(key, expire);
				}
				p.sync();
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	
	public void deleteKeyPattern(String pattern){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			Set<String> keys = jedis.keys(pattern);
			for (String key : keys) {
				jedis.del(key);
			}
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	public  Map<String,String> hmget(String key,String... fields){
 		Map<String,String> mapdata=new HashMap<String,String>();
 		Jedis jedis = null;
 		try{
 			jedis = getJedis();
 			List<String> list=jedis.hmget(key, fields);
 			if(list!=null&&list.size()>0){
 				for(int i=0;i<fields.length;i++){
 					mapdata.put(fields[i], list.get(i));
 				}
 			}
 		}catch(Exception e){
 			log.error("redis执行异常!!!",e);
 			removeJedisCache(jedis);
 		}finally{
 			closeJedis(jedis);
 		}
 		return mapdata;
 	}
	
	
	public   void hdelBatch(String key,Set<String> values){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			Pipeline p = jedis.pipelined();
			if(values != null && 0 < values.size()){
				for(String value : values){
					p.hdel(key, value);
				}
			}
			p.sync();
		}catch(Exception e){
			log.error("redis执行异常!!!",e);
			removeJedisCache(jedis);
		}finally{
			closeJedis(jedis);
		}
	}
}
