package com.knight.wanandroid.module_hierachy;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.listener.AppInit;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 19:16
 * @descript:
 */
public final class HierachyApp extends BaseApp implements AppInit {

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

    @Override
    public void initModuleData(Application application) {

    }

    @Override
    public void applicationInit(@NonNull Application application) {
        initModuleApp(this);
        initModuleData(this);
    }
}
