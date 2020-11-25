/**
 * Copyright(C) 2015-2017 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2015-2017 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.7.0_67
 * <p>
 * 版本: 1.0版 文件名：com.genertech.phm.fault.TestFaultInfoAction.java
 * <p>
 * 作者: 郑阳文
 * <p>
 * 创建时间: 2017年3月13日下午4:42:54
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：郑阳文
 * <p>
 * 修改时间：2017年3月13日下午4:42:54
 * <p>
 */
package com.genertech.phm.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.genertech.phm.core.identifier.service.IdProvider;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional(readOnly=true)
public class TestIdProvider {

    @Autowired
    private IdProvider idp;

    @Test
    public void testNextVal() throws Exception {
        System.out.println(idp.nextVal("00001"));
    }
}
