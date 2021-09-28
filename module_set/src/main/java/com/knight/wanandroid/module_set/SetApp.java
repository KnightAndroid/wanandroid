package com.knight.wanandroid.module_set;

import android.app.Application;
import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_util.LogUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/16 10:15
 * @descript:
 */
public final class SetApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("1111进来进入SetApp");
        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    protected void initSafeSDK() {
        LogUtils.d("1111进来初始化安全sdkSetApp");
        if ("true".equals(BuildConfig.isAloneApp)) {
            ModuleConfig.getInstance().initSafeSdk(this);
        }
    }

    @Override
    protected void initDangerousSDK() {
        LogUtils.d("1111进来初始化危险sdkSetApp");
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
}
