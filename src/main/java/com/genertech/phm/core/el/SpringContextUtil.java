/**
 * Copyright(C) 2015-2017 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2015-2017 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.7.0_67
 * <p>
 * 版本: 1.0版 文件名：com.genertech.phm.core.el.SpringContentUtils.java
 * <p>
 * 作者: 郑阳文
 * <p>
 * 创建时间: 2017年4月7日下午5:26:20
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：郑阳文
 * <p>
 * 修改时间：2017年4月7日下午5:26:20
 * <p>
 */
package com.genertech.phm.core.el;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {
    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    public static void setAppCtx(ApplicationContext ctx) {
        if (SpringContextUtil.applicationContext == null)
            SpringContextUtil.applicationContext = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     * 
     * @param name
     * @return Object
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
    
    /**
     * 获取对象
     * 
     * @param name
     * @param type
     * @return Object
     * @throws BeansException
     */
    public static <T> T getBean(String name, Class<T> type) throws BeansException {
        return applicationContext.getBean(name, type);
    }
    
    /**
     * 获取对象
     * 
     * @param type
     * @return Object
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> type) throws BeansException {
        return applicationContext.getBean(type);
    }

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     * 
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }
}
