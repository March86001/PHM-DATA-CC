package com.genertech.phm.core.redis.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis工具类,用于获取RedisPool.
 * 参考官网说明如下：
 * You shouldn't use the same instance from different threads because you'll have strange errors.
 * And sometimes creating lots of Jedis instances is not good enough because it means lots of sockets and connections,
 * which leads to strange errors as well. A single Jedis instance is not threadsafe!
 * To avoid these problems, you should use JedisPool, which is a threadsafe pool of network connections.
 * This way you can overcome those strange errors and achieve great performance.
 * To use it, init a pool:
 *	JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
 *	You can store the pool somewhere statically, it is thread-safe.
 *	JedisPoolConfig includes a number of helpful Redis-specific connection pooling defaults.
 *	For example, Jedis with JedisPoolConfig will close a connection after 300 seconds if it has not been returned.
 * @author wujintao
 */
public class JedisUtil  {
	private  static Logger log=Logger.getLogger(JedisUtil.class);
	/**
	 * 私有构造器.
	 */
	private JedisUtil() {
	    
	}
	private static Map<String,JedisPool> maps  = new HashMap<String,JedisPool>();
	
	private static JedisPoolConfig config = null;
	static{
		   
		  config = new JedisPoolConfig();
		  config.setMaxActive(PropertiesProvider.getIntegerProperty("redis.pool.maxActive"));
          config.setMaxIdle(PropertiesProvider.getIntegerProperty("redis.pool.maxIdle"));
          config.setMaxWait(PropertiesProvider.getIntegerProperty("redis.pool.maxWait"));
          String strTestOnBorrow= PropertiesProvider.getProperty("redis.pool.testOnBorrow");
          if(null !=strTestOnBorrow && "true".equalsIgnoreCase(strTestOnBorrow)){
          	 config.setTestOnBorrow(true);
          } else {
          	config.setTestOnBorrow(false);
          }
          
          String strTestWhileIdle= PropertiesProvider.getProperty("redis.pool.testWhileIdle");
          if(null !=strTestWhileIdle && "true".equalsIgnoreCase(strTestWhileIdle)){
           	 config.setTestWhileIdle(true);
           } else {
           	config.setTestWhileIdle(false);
           }
          
          String strTestOnReturn= PropertiesProvider.getProperty("redis.pool.testOnReturn");
          if(null != strTestOnReturn && "true".equalsIgnoreCase(strTestOnReturn)){
          	 config.setTestOnReturn(true);
          } else {
          	 config.setTestOnReturn(false);
          }
	}
    
    /**
     * 获取连接池.
     * @return 连接池实例
     */
	public static JedisPool getPool(String ip,int port) {
		String key = ip+":" +port;
		JedisPool pool = null;
		if(maps.get(key)==null) {
			try{  
				pool = new JedisPool(config, ip, port,PropertiesProvider.getIntegerProperty("redis.timeout"),PropertiesProvider.getProperty("redis.password"));
				maps.put(key, pool);
			} catch(Exception e) {
				if(pool!=null){
					pool.destroy();
				}
			}
		}else{
			pool = maps.get(key);
		}
		return pool;
	}

    
    
    /**
     *类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     *没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class RedisUtilHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static JedisUtil instance = new JedisUtil();
    }

    /**
     *当getInstance方法第一次被调用的时候，它第一次读取
     *RedisUtilHolder.instance，导致RedisUtilHolder类得到初始化；而这个类在装载并被初始化的时候，会初始化它的静
     *态域，从而创建RedisUtil的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，并由虚拟机来保证它的线程安全性。
     *这个模式的优势在于，getInstance方法并没有被同步，并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本。
     */
	public static JedisUtil getInstance() {
		return RedisUtilHolder.instance;
	}
	
	/**
	 * 获取Redis实例.
	 * @return Redis工具类实例
	 */
	public synchronized   static Jedis getJedis(String ip,int port) {
		Jedis jedis  = null;
		int count =0;
		do{
			try{ 
				jedis = getPool(ip,port).getResource();
			} catch (Exception e) {
				log.error("get redis master1 failed!", e);
				removeJedisCache(jedis, ip, port);
			}
			count++;
		}while(jedis==null&&count<PropertiesProvider.getIntegerProperty("redis.retrynum"));
		return jedis;
	}

	/**
	 * 释放redis实例到连接池.
     * @param jedis redis实例
     */
	public synchronized   static void closeJedis(Jedis jedis,String ip,int port) {
		if(jedis != null) {
			String key = ip+":" +port;
			JedisPool pool=maps.get(key);
			if(pool!=null&&jedis!=null){
				try{
				pool.returnResource(jedis);
				}catch(Exception e){
					jedis.quit();
					jedis.disconnect();
				}
				
			}
		}
	}
	

    

    
	public synchronized   static void removeJedisCache(Jedis jedis,String ip, int port) {
		String key = ip+":" +port;
		if(maps.get(key)!=null) {
			JedisPool pool=maps.remove(key);
			if(pool!=null){
				if(jedis != null) {
					pool.returnBrokenResource(jedis);
				}
				pool.destroy();
			}
		}
	}
    
}
