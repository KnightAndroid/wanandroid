package com.knight.wanandroid.module_web;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/10 18:06
 * @descript:
 */
public final class WebApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if ("true".equals(BuildConfig.isAloneApp)) {
            ModuleConfig.getInstance().initBefore(this);
            ModuleConfig.getInstance().initModuleAfter(this);
        }
    }
    @Override
    public void initModuleApp(Application application) {

    }

    @Override
    public void initModuleData(Application application) {

    }
}
