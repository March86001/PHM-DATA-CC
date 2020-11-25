/**
 * MyBeanUtils.java Create on 2013-8-16 Copyright(c) Gener-Tech Inc. ALL Rights Reserved.
 */
package com.genertech.phm.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.genertech.phm.utils.annotation.GenertechMapper;

/**
 * 
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author <a href="mailto:liu.w@gener-tech.com">liuwei</a>
 * @version 1.0
 */
public class GenertechBeanUtils extends BeanUtils {

    public static void copyProperties(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等//
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * 把List中的所有map转成对应的javaBean集
     * 支持@GenertechMapper注解 <br/>
     * 并且支持以数据库命名方式的字段转到以java命名方式的字段上
     * 
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     *
     * @author 创建人：郑阳文   2017年3月14日 下午5:36:02
     * @author 修改人：郑阳文   2017年3月14日 下午5:36:02
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws IllegalArgumentException 
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> mapsToEntitys(List maps, Class<T> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException  {
        if (maps == null || maps.isEmpty()) return null;
        List<T> list = new ArrayList<T>();
        for (Object obj : maps) {
            if (obj instanceof Map) {// 只转换map
                Map map = (Map) obj;
                T t = mapToEntity(map, clazz);
                if (t != null) list.add(t);
            }
        }
        return list;
    }

    /**
     * 把map转成javaBean <br/>
     * 支持@GenertechMapper注解 <br/>
     * 并且支持以数据库命名方式的字段转到以java命名方式的字段上
     * 
     * @param map
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     *
     * @author 创建人：郑阳文   2017年3月14日 下午5:37:06
     * @author 修改人：郑阳文   2017年3月14日 下午5:37:06
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws IllegalArgumentException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T mapToEntity(Map map, Class<T> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException  {
        if (map == null || map.isEmpty()) return null;

        T bean = clazz.newInstance();

        Map<String, String> anttFields = new HashMap<String, String>();
        Map<String, Method> mapMethods = new HashMap<String, Method>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set")) {
                String fieldName = methodName.substring(3).toLowerCase();
                //fieldName = firstLowerCase(fieldName);
                mapMethods.put(fieldName, method);
                // 获取注解
                GenertechMapper agm = method.getAnnotation(GenertechMapper.class);
                if (agm != null && StringUtils.isNotEmpty(agm.value())) {
                    anttFields.put(agm.value().toLowerCase(), fieldName);
                }
            }
        }

        Iterator names = map.keySet().iterator();
        while (names.hasNext()) {
            String name = (String) names.next();
            if (name != null) {
                Object value = map.get(name);
                name = name.toLowerCase();
                boolean hasField = false;
                if (mapMethods.containsKey(name)) {
                    hasField = true;
                } else if (anttFields.containsKey(name)) {
                    name = anttFields.get(name);
                    hasField = mapMethods.containsKey(name);
                } else {
                    // 自动把数据命名方式的字段对应java命名字段
                	// name = getJavaFieldName(name);
                	name = name.replace("_", "");
                    hasField = mapMethods.containsKey(name);
                }
                if (hasField) {
                    Method method = mapMethods.get(name);
                    Type paramType = method.getGenericParameterTypes()[0];
                    Class claxx = (Class)paramType;
                    if (value instanceof Map && claxx != Map.class)
                        value = mapToEntity((Map)value, claxx);
                    else
                        value = TypeUtils.cast(value, paramType, ParserConfig.getGlobalInstance());
                    method.invoke(bean, new Object[] { value });
                }
            }
        }
        return bean;
    }
    
    /**
     * 把字符串为数据库命名的方式转为java命名方式
     * 
     * @param field
     *            数据库命名方式字段
     * @return java命名方式字段
     * 
     * @author 创建人：郑阳文 2017年3月14日 下午4:00:50
     * @author 修改人：郑阳文 2017年3月14日 下午4:00:50
     */
    public static String getJavaFieldName(String field) {
        if (StringUtils.isEmpty(field)) return field;
        field = field.toLowerCase();
        String[] parts = field.split("_");
        field = "";
        for (String p : parts) {
            if (field.equals("")) field = p;
            else field += p.substring(0, 1).toUpperCase() + p.substring(1);
        }
        return field;
    }
    
    /**
     * 首字母小写
     * 
     * @param str
     * @return
     *
     * @author 创建人：郑阳文   2017年3月14日 下午6:23:36
     * @author 修改人：郑阳文   2017年3月14日 下午6:23:36
     */
    public static String firstLowerCase(String str) {
        if (StringUtils.isEmpty(str)) return str;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

}
