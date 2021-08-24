package com.knight.wanandroid.module_mine;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/25 10:30
 * @descript:我的moduleApp
 */
public final class MineApp extends BaseApp {


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
