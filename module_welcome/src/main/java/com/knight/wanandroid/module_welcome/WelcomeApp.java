package com.knight.wanandroid.module_welcome;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.listener.AppInit;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 17:46
 * @descript:
 */
public final class WelcomeApp extends BaseApp implements AppInit {
    @Override
    public void onCreate() {
        super.onCreate();
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

    @Override
    public void applicationInit(@NonNull Application application) {
        initModuleApp(this);
        initModuleData(this);
    }
}
