/**
 * Copyright(C) 2015-2017 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>  
 * 版权所有(C)2015-2017 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.7.0_67
 * <p>
 * 版本: 1.0版
 * 文件名：com.genertech.phm.utils.annotation.GenertechMapper.java
 * <p>
 * 作者: 郑阳文
 * <p>
 * 创建时间: 2017年3月14日下午2:47:26
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p> 
 * 修改者：郑阳文
 * <p>
 * 修改时间：2017年3月14日下午2:47:26
 * <p>
 */
package com.genertech.phm.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用自定义GenertechBeanUtils.mapToEntity方法时把字段名不一样的key的值存到bean的指定字段中
 * 如：map里有USER_NAME，而bean里面只有name时他们无法转换。
 * 这时就可以在bean的name对应的set方法上加@GenertechMapper("USER_NAME")便能正确转换
 * 如果bean里面定义的字段是userName，不用注解也可以正确转换
 * 
 * @author 创建人：<a href="mailto:sunlf@gener-tech.com">郑阳文</a> 2017年3月14日 下午2:51:18
 * @author 修改人：<a href="mailto:sunlf@gener-tech.com">郑阳文</a> 2017年3月14日 下午2:51:18
 * @since JDK 1.7.0_67
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface GenertechMapper {
    
    String value();
    
}
