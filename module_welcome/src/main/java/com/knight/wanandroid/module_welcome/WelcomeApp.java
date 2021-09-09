package com.knight.wanandroid.module_welcome;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_util.LogUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 17:46
 * @descript:
 */
public final class WelcomeApp extends BaseApp {
    @Override
    public void onCreate(){
        super.onCreate();
        if ("true".equals(BuildConfig.isAloneApp)) {
            LogUtils.d("123456");
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
