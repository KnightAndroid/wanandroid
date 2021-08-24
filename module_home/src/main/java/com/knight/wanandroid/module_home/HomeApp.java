package com.knight.wanandroid.module_home;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/29 16:46
 * @descript: home module下的application
 */
public final class HomeApp extends BaseApp {



    @Override
    public void onCreate(){
        super.onCreate();
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
