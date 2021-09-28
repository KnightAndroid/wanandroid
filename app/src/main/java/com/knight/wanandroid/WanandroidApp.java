package com.knight.wanandroid;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_util.ActivityManagerUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 19:56
 * @descript:
 */
public class WanandroidApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);
    }


    /**
     *
     * 初始化危险类sdk
     */
    @Override
    protected void initDangerousSDK() {
        ModuleConfig.getInstance().initDangerousSDK(this);
    }


    /**
     * 初始化那些和用户隐私无关的SDK
     * 如果无法区分，建议只使用initSDK一个方法
     */
    @Override
    public void initSafeSDK() {
        ModuleConfig.getInstance().initSafeSdk(this);
        registerActivityLifecycleCallbacks(ActivityManagerUtils.getInstance());

    }

    @Override
    public void initModuleApp(Application application) {
        ModuleConfig.getInstance().initModuleAfter(application);
    }

    @Override
    public void initModuleData(Application application) {

    }


}
