package com.knight.wanandroid.library_common;

import android.app.Application;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 14:32
 * @descript:application 提供
 */
public class AppBridge {
    public static Application getApplicationByReflect () {
        return AppField.INSTANCE.getApplicationByReflect();
    }
}
