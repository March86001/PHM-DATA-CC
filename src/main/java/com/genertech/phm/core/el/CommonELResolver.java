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
 * 文 件 名：com.genertech.phm.core.el.CommonELResolver.java
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
 * 创建时间：2015-3-18上午10:25:05
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

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;

/**
 * <p>
 * 描 述：EL表达式动态解析类
 * </p>
 * <p>
 * 创 建 人：Jimmy
 * </p>
 * <p>
 * 创建时间：2015-3-18上午10:25:05
 * </p>
 */
public class CommonELResolver {

    // ExpressionFactory类的实现是de.odysseus.el.ExpressionFactoryImpl
    private static ExpressionFactory factory = new ExpressionFactoryImpl();
    // de.odysseus.el.util provides包提供即时可用的子类ELContext
    private SimpleContext            context = new SimpleContext();

    public CommonELResolver() {
    }

    /**
     * 描述：添加参数
     * 
     * @param name
     * @param value
     */
    public void addVariable(String name, Object value) {
        if (value != null) context.setVariable(name, factory.createValueExpression(value, value.getClass()));
        else context.setVariable(name, factory.createValueExpression(value, Object.class));
    }

    /**
     * 描述：添加方法
     * 
     * @param name
     * @param value
     */
    public void addFunction(String name, Method value) {
        context.setFunction(name, value);
    }

    public void setVariableMap(Map<String, Object> dataMap) {
        for (String key : dataMap.keySet()) {
            this.addVariable(key, dataMap.get(key));
        }
    }

    public static Object getBean(String beanId) {
        return SpringContextUtil.getBean(beanId);
    }

    public static ValueExpression getBeanValueExpression(String beanId) {
        Object value = getBean(beanId);
        if (value != null) return factory.createValueExpression(value, value.getClass());
        else return factory.createValueExpression(value, Object.class);
    }

    /**
     * 描述：计算表达式结果
     * 
     * @param input
     * @return
     */
    public String evaluate(String input) {
        if (input == null || input.equals("")) return input;
        // 解析表达式
        Object result = null;
        try {
            ValueExpression e = factory.createValueExpression(context, input, Object.class);
            result = e.getValue(context);
        } catch (Exception e) {

        }
        return result == null ? "" : result.toString();
    }

    /**
     * 描述：获取默认解析器实例 有sysdate(), formatDate(date, format)等方法
     * 
     * @return
     */
    public static CommonELResolver getDefaultResolver() {
        CommonELResolver rs = new CommonELResolver();
        try {
            rs.addFunction("sysdate", ELFunction.class.getMethod("sysdate"));
            rs.addFunction("sysdateStr", ELFunction.class.getMethod("sysdateStr", String.class));
            rs.addFunction("formateDate", ELFunction.class.getMethod("formateDate", Date.class, String.class));

            rs.addFunction("addDate", ELFunction.class.getMethod("addDate", Date.class, Integer.class));
            rs.addFunction("addCurrDate", ELFunction.class.getMethod("addCurrDate", Integer.class));
            rs.addFunction("addDateStr", ELFunction.class.getMethod("addDateStr", Date.class, Integer.class, String.class));
            rs.addFunction("addCurrDateStr", ELFunction.class.getMethod("addCurrDateStr", Integer.class, String.class));

            rs.addFunction("leftPad", ELFunction.class.getMethod("leftPad", String.class, Integer.class, String.class));
            rs.addFunction("rightPad", ELFunction.class.getMethod("rightPad", String.class, Integer.class, String.class));

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 描述：获取默认Web解析器实例 有ApplicationContext里面所有bean
     * 
     * @return
     */
    public static CommonELResolver getDefaultWebResolver() {
        CommonELResolver rs = getDefaultResolver();
        rs.context.setContainsBeans(true);
        return rs;
    }

    public static void main(String[] args) {
        CommonELResolver rs = CommonELResolver.getDefaultResolver();
        rs.addVariable("a", "aaa");
        rs.addVariable("b", new CommonELResolver());

        System.out.println(rs.evaluate("${formateDate(sysdate(), 'yyyy')}"));
    }

}
