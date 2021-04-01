package com.knight.wanandroid.module_square;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 19:42
 * @descript:
 */
public class SquareApp extends BaseApp {

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
