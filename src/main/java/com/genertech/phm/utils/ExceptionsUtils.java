/**
 * Copyright (c) 2005-2012 gener-tech.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.genertech.phm.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 关于异常的工具类.
 * 
 * @author calvin
 */
public class ExceptionsUtils {

    /**
     * 将ErrorStack转化为String.
     * 
     * @param t
     * @return
     * 
     * @author 创建人：郑阳文 2017年3月13日 下午6:12:04
     * @author 修改人：郑阳文 2017年3月13日 下午6:12:04
     */
    public static String getStackTraceAsString(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.getBuffer().toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }
}
