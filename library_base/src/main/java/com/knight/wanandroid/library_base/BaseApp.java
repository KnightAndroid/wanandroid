package com.knight.wanandroid.library_base;

import android.app.Application;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 18:49
 * @descript:描述BaseApp
 */
public abstract class BaseApp extends Application {


    /**
     *
     *  Application 初始化
     *
     */
    public abstract void initModuleApp(Application application);


    /**
     *
     * 所有Application 初始化后的自定义操作
     *
     */
    public abstract void initModuleData(Application application);


}
