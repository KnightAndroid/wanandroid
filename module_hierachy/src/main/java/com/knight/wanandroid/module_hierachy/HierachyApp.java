package com.knight.wanandroid.module_hierachy;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 19:16
 * @descript:
 */
public final class HierachyApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if ("true".equals(BuildConfig.isAloneApp)) {
            ModuleConfig.getInstance().initBefore(this);
            ModuleConfig.getInstance().initModuleAfter(this);
        }
        initModuleApp(this);
        initModuleData(this);
    }
    @Override
    public void initModuleApp(Application application) {

    }

    @Override
    public void initModuleData(Application application) {

    }
}
