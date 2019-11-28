package com.dxt.common;

/**
 * 保存一个线程安全的MyDatabaseSource容器
 */
public class DatabaseContextHolder {

    private static final ThreadLocal<MyDatabaseSource> contextHolder = new ThreadLocal<>();

    public static void setDatabaseSource(MyDatabaseSource source) {
        contextHolder.set(source);
    }

    public static MyDatabaseSource getDatabaseSource() {
        return contextHolder.get();
    }

    public static void removeDatabaseSource() {
        contextHolder.remove();
    }

}
