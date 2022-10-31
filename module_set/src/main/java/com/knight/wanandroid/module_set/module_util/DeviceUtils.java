package com.knight.wanandroid.module_set.module_util;

import android.os.Build;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Author:Knight
 * Time:2022/10/31 9:49
 * Description:DeviceUtils 设备工具类
 */
public class DeviceUtils {

    /**
     *
     * 获取系统版本
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     *
     * 获取安卓SDK版本
     * @return
     */
    public static int getAndroidSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     *
     * 返回时区
     * @return
     */
    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    /**
     *
     * 返回时区名
     * @return
     */
    public static String getTimeZone() {
        return TimeZone.getDefault().getID();
    }

}
