package com.knight.wanandroid.library_network;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:23
 * @descript:日志类
 */
public final class EasyLog {

    /**
     * 打印分割线
     */
    public static void print() {
        print("----------------------------------------");
    }

    /**
     * 打印日志
     */
    public static void print(String log) {
        if (HttpConfig.getInstance().isLogEnabled()) {
            HttpConfig.getInstance().getLogStrategy().print(log);
        }
    }

    /**
     * 打印 Json
     */
    public static void json(String json) {
        if (HttpConfig.getInstance().isLogEnabled()) {
            HttpConfig.getInstance().getLogStrategy().json(json);
        }
    }

    /**
     * 打印异常
     */
    public static void print(Throwable throwable) {
        if (HttpConfig.getInstance().isLogEnabled()) {
            HttpConfig.getInstance().getLogStrategy().print(throwable);
        }
    }

    /**
     * 打印键值对
     */
    public static void print(String key, String value) {
        if (HttpConfig.getInstance().isLogEnabled()) {
            HttpConfig.getInstance().getLogStrategy().print(key, value);
        }
    }
}