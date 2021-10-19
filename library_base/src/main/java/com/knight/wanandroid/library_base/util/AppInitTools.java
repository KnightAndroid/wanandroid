package com.knight.wanandroid.library_base.util;

import android.app.Application;

import com.knight.wanandroid.library_base.listener.AppInit;

import java.util.Iterator;
import java.util.ServiceLoader;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/19 14:13
 * @descript:
 */
public class AppInitTools {
    private static volatile AppInitTools manager;

    private AppInitTools() {

    }

    public static AppInitTools getInstance() {
        if (manager == null) {
            synchronized (AppInitTools.class) {
                if (manager == null) {
                    manager = new AppInitTools();
                }
            }
        }
        return manager;
    }


    /**
     * 初始化各个moduel的Application
     * @param application
     */
    public void initAllModuelApplication(@NonNull Application application) {
        ServiceLoader<AppInit> loader = ServiceLoader.load(AppInit.class);
        Iterator<AppInit> mIterator = loader.iterator();
        while (mIterator.hasNext()) {
            mIterator.next().applicationInit(application);
        }
    }
}
