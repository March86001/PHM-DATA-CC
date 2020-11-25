package com.genertech.core.db;

/**
 * 保持数据源key
 * @author liuqiang
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    
    public static void setDbType(String dbType) {
    	contextHolder.set(dbType);
    }

    public static String getDbType() {
        return contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
