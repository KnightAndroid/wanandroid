package com.knight.wanandroid;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 19:56
 * @descript:
 */
public class WanandroidApp extends BaseApp {



    @Override
    public void onCreate(){
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    public void initModuleApp(Application application) {
        ModuleConfig.getInstance().initBefore(application);
        ModuleConfig.getInstance().initModuleAfter(application);
    }

    @Override
    public void initModuleData(Application application) {

    }
}
