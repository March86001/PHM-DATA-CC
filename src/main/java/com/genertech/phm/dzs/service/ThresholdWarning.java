package com.genertech.phm.dzs.service;

import com.alibaba.fastjson.JSON;
import com.genertech.phm.dzs.dto.FaultWarn;
import com.genertech.phm.utils.DateUtils;
import com.genertech.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.*;

public class ThresholdWarning {
    public static void main(String[] args) {
        ThresholdWarning p = new ThresholdWarning();
        p.getThresholdWarning();
    }
    public static int redisPort = 6379;
    public List<FaultWarn> getThresholdWarning() {
        List<FaultWarn> fws = new ArrayList<FaultWarn>();
        Date sysdate = new Date();
        String dateStr = DateUtils.dateToString(sysdate, "yyyyMMdd");
        //String dateStr = "20201029";
        String ip1 = "10.217.242.103";
        String ip2 = "10.217.242.111";
        String ip3 = "10.217.242.112";
        String ip4 = "10.217.242.113";
        String ip5 = "10.217.242.190";
        String ip6 = "10.217.242.252";
        String ip7 = "10.217.242.114";
        Map<String, String> m1 = getThresholdWarningFromRedis(ip1, dateStr);
        Map<String, String> m2 = getThresholdWarningFromRedis(ip2, dateStr);
        Map<String, String> m3 = getThresholdWarningFromRedis(ip3, dateStr);
        Map<String, String> m4 = getThresholdWarningFromRedis(ip4, dateStr);
        Map<String, String> m5 = getThresholdWarningFromRedis(ip5, dateStr);
        Map<String, String> m6 = getThresholdWarningFromRedis(ip6, dateStr);
        Map<String, String> m7 = getThresholdWarningFromRedis(ip7, dateStr);
        Map<String, String> combineResultMap = new HashMap<String, String>();
        combineResultMap.putAll(m1);
        combineResultMap.putAll(m2);
        combineResultMap.putAll(m3);
        combineResultMap.putAll(m4);
        combineResultMap.putAll(m5);
        combineResultMap.putAll(m6);
        combineResultMap.putAll(m7);
        for (Map.Entry<String, String> entry : combineResultMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //if (key.startsWith("长客标动")) {
            FaultWarn faultWarnBean = JSON.parseObject(value, FaultWarn.class);
            String kk = key + ":" + faultWarnBean.getOccrStatus();
            //用当天发生的预警和状态判断
            if (getWaringKey(ip2, dateStr, kk)) {
                //判断是否发送过
                continue;
            }
            //最新发生恢复时间，传给铁科
            setWaringKey(ip2, dateStr, kk);
            String lastTime = faultWarnBean.getCreateTime();
            List<Map<String, Map<String, Object>>> mm = faultWarnBean.getRecInfosList();
            for (Map<String, Map<String, Object>> m : mm) {
                for (Map.Entry<String, Map<String, Object>> mf : m.entrySet()) {
                    String currentTime = (String) mf.getValue().get("currentTime");
                    //判断是否有过状态更新,更新预警时间
                    if (Long.parseLong(currentTime) > Long.parseLong(lastTime)) {
                        lastTime = currentTime;
                        faultWarnBean.setCreateTime(lastTime);
                    }
                }
            }
            fws.add(faultWarnBean);
            //}
        }
        return fws;
    }

    public void setWaringKey(String redisIp, String dateStr, String key) {
        Jedis jedis = JedisUtil.getJedis(redisIp, redisPort);
        try {
            jedis.sadd("WARN_REC_KEY" + ":" + dateStr, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                JedisUtil.closeJedis(jedis, redisIp, redisPort);
            }
        }
    }
    public boolean getWaringKey(String redisIp, String dateStr, String key) {
        Jedis jedis = JedisUtil.getJedis(redisIp, redisPort);
        try {
            return jedis.sismember("WARN_REC_KEY" + ":" + dateStr, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                JedisUtil.closeJedis(jedis, redisIp, redisPort);
            }
        }
        return true;
    }

    public Map<String, String> getThresholdWarningFromRedis(String redisIp, String dateStr) {
        Jedis jedis = JedisUtil.getJedis(redisIp, redisPort);
        Map<String, String> warnRec = new HashMap<String, String>();
        try {
            warnRec = jedis.hgetAll("WARN_REC" + ":" + dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                JedisUtil.closeJedis(jedis, redisIp, redisPort);
            }
        }
        return warnRec;
    }

}