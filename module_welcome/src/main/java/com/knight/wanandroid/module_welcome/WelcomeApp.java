package com.knight.wanandroid.module_welcome;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 17:46
 * @descript:
 */
public final class WelcomeApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    protected void initSafeSDK() {
        if ("true".equals(BuildConfig.isAloneApp)) {
            ModuleConfig.getInstance().initSafeSdk(this);
        }

    }

    @Override
    protected void initDangerousSDK() {
        if ("true".equals(BuildConfig.isAloneApp)) {
            ModuleConfig.getInstance().initDangerousSDK(this);
        }

    }


    @Override
    public void initModuleApp(Application application) {

    }

    /**
     * 初始化app数据
     *
     * @param application
     */
    @Override
    public void initModuleData(Application application) {

    }
}
