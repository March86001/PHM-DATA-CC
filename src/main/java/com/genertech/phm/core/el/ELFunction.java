/**
 * Copyright(C) 2012-2015 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2015 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>
 * 标 题：
 * </p>
 * <p>
 * 文 件 名：com.genertech.phm.core.el.ELFunction.java
 * </p>
 * <p>
 * 部 门：杭北项目组
 * <p>
 * 版 本： 1.0
 * </p>
 * <p>
 * Compiler: JDK1.6.0_21
 * </p>
 * <p>
 * 创 建 者：Jimmy
 * </p>
 * <p>
 * 创建时间：2015-3-18下午2:31:51
 * </p>
 * <p>
 * 修 改 者：
 * </p>
 * <p>
 * 修改时间：
 * </p>
 */
/**
 * 
 */
package com.genertech.phm.core.el;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 描 述：EL表达式中所用的通用的方法类
 * </p>
 * <p>
 * 创 建 人：Jimmy
 * </p>
 * <p>
 * 创建时间：2015-3-18下午2:31:51
 * </p>
 */

public class ELFunction
{

    /**
     * 描述：格式化时间
     * @param date
     * @param format
     * @return
     */
    public static String formateDate(Date date, String format)
    {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 描述：获取系统时间
     * @return
     */
    public static Date sysdate()
    {
        return new Date();
    }
    
    /**
     * 描述：获取系统时间,返回指定format格式字符串
     * @return
     */
    public static String sysdateStr(String format)
    {
    	return formateDate(new Date(), format);
    }
    
    /**
     * 描述：日期计算，当前时间加day天
     * @return
     */
    public static Date addCurrDate(Integer day)
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, day);
    	return calendar.getTime();
    }
    
    /**
     * 描述：日期计算，给指定date时间加day天
     * @return
     */
    public static Date addDate(Date date, Integer day)
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE, day);
    	return calendar.getTime();
    }
    
    /**
     * 描述：日期计算，当前时间加day天，返回指定format格式字符串
     * @return
     */
    public static String addCurrDateStr(Integer day, String format)
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, day);
    	return formateDate(calendar.getTime(), format);
    }
    
    /**
     * 描述：日期计算，给指定date时间加day天，返回指定format格式字符串
     * @return
     */
    public static String addDateStr(Date date, Integer day, String format)
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE, day);
    	return formateDate(calendar.getTime(), format);
    }
    
    /**
     * 描述：日期计算，给指定format格式字符串date时间 加day天，返回指定format格式字符串
     * @return
     */
    public static String addStrDateStr(String date, Integer day, String format)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	Calendar calendar = Calendar.getInstance();
    	try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	calendar.add(Calendar.DATE, day);
    	return sdf.format(calendar.getTime());
    }
    
    /**
     * rightPad
     * @param str
     * @param size
     * @param padStr
     * @return
     */
    public static String rightPad(String str, Integer size, String padStr)
    {
    	return StringUtils.rightPad(str, size, padStr);
    }
    
    /**
     * leftPad
     * @param str
     * @param size
     * @param padStr
     * @return
     */
    public static String leftPad(String str, Integer size, String padStr)
    {
    	return StringUtils.leftPad(str, size, padStr);
    }
    
}
