package com.knight.wanandroid.library_base.listener;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/19 14:00
 * @descript:
 */
public interface AppInit {

    /**
     *
     * 初始化各个module
     * @param application
     */
    void applicationInit(@NonNull Application application);
}
