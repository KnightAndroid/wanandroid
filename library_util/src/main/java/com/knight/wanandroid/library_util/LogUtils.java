package com.knight.wanandroid.library_util;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 17:41
 * @descript:日志工具
 */
public class LogUtils {
    private static final String DEFAULT_TAG = "wanandroidApp";
    private static boolean isLog = BuildConfig.DEBUG;
    private LogUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isLog() {
        return isLog;
    }

    public static void setLog(boolean isLog) {
        LogUtils.isLog = isLog;
    }

    public static void debugInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.d(tag, msg);

    }


    public static void errorInfo(String msg){
        Log.e(DEFAULT_TAG, msg);
    }

    public static void debugInfo(String msg) {
        debugInfo(DEFAULT_TAG, msg);
    }

    public static void warnInfo(String tag, String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.w(tag, msg);

    }

    public static void warnInfo(String msg) {
        warnInfo(DEFAULT_TAG, msg);
    }

    /**
     * 这里使用自己分节的方式来输出足够长度的 message
     *
     *
     * @param msg 日志内容
     */
    public static void debugLongInfo(String msg) {
        if (!isLog || TextUtils.isEmpty(msg)) {
            return;
        }
        msg = msg.trim();
        int index = 0;
        int maxLength = 3500;
        String sub;
        while (index < msg.length()) {
            if (msg.length() <= index + maxLength) {
                sub = msg.substring(index);
            } else {
                sub = msg.substring(index, index + maxLength);
            }

            index += maxLength;
            Log.d(DEFAULT_TAG, sub.trim());
        }
    }

    public static void d(String message){
        if(isLog){
            int segmentSize = 3 * 1024;
            long length = message.length();
            if(length <= segmentSize){
                Log.d(DEFAULT_TAG,message);
            }else{
                while (message.length() > segmentSize){
                    String logContent = message.substring(0,segmentSize);
                    message = message.replace(logContent,"");
                    Log.d(DEFAULT_TAG,logContent);
                }
                Log.d(DEFAULT_TAG,message);
            }

        }
    }
}
