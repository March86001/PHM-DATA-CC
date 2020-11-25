package com.genertech.phm.gener;

import com.genertech.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* @author liuqiang
* @date 2020年3月25日
*/
public class CacheTest {

	public static void main(String[] args) {
		CacheTest ct = new CacheTest();
		ct.cache();
	}

	class JedisThread extends Thread {
		public void run() {
			Jedis jedis = JedisUtil.getJedis();
			System.out.println(jedis);
			Map<String, String> s = jedis.hgetAll("WARN_REC:20201026");
			JedisUtil.closeJedis(jedis, "172.22.172.252", 6379);
		}
	}

	private void cache() {
		for (int i = 0; i< 50; i++) {
			Thread t1 = new Thread(new JedisThread());
			t1.start();
		}

	}

}
