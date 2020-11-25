/**
 * Copyright(C) 2015-2017 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2015-2017 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.7.0_67
 * <p>
 * 版本: 1.0版 文件名：com.genertech.phm.core.Runner.java
 * <p>
 * 作者: 郑阳文
 * <p>
 * 创建时间: 2017年3月15日上午11:45:30
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：郑阳文
 * <p>
 * 修改时间：2017年3月15日上午11:45:30
 * <p>
 */
package com.genertech.phm.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.genertech.phm.core.el.SpringContextUtil;
import com.genertech.phm.utils.DateUtils;

/**
 * 启动spring容器
 * 
 * @author 创建人：<a href="mailto:sunlf@gener-tech.com">郑阳文</a> 2017年3月15日 上午11:50:16
 * @author 修改人：<a href="mailto:sunlf@gener-tech.com">郑阳文</a> 2017年3月15日 上午11:50:16
 * @since JDK 1.7.0_67
 */
public class Runner {
    private static final Log logger = LogFactory.getLog(Runner.class);

    public static void main(String[] args) {
        /*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:applicationContext.xml" });
        SpringContextUtil.setAppCtx(context);*/
        logger.info("spring启动 --> " + DateUtils.getCurrentDateTimeStr());
    }

}
