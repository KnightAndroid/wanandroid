package com.knight.wanandroid.library_base.initconfig;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.BuildConfig;
import com.knight.wanandroid.library_base.interceptor.PermissionInterceptor;
import com.knight.wanandroid.library_permiss.XXPermissions;
import com.knight.wanandroid.library_util.ToastUtils;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 19:50
 * @descript:
 */
public class ModuleConfig {


    private ModuleConfig(){

    }

    private static class SingleHolder{
        private static ModuleConfig instance = new ModuleConfig();
    }

    public static ModuleConfig getInstance(){
        return SingleHolder.instance;
    }


    /**
     *
     * 最新初始化
     * @param application
     */
    public void initBefore(@Nullable Application application){
        //初始化ARouter
        if(isDebug()){
            //如果是debug模式
            ARouter.openLog();
            ARouter.openDebug();
        }

        //初始化路由
        ARouter.init(application);
        //初始化Toast
        ToastUtils.getInstance().initApplicaion(application);
        //初始化权限拦截器
        XXPermissions.setPermissionInterceptor(new PermissionInterceptor());
    }
    /**
     *
     * 后面初始化
     * @param application
     */
    public void initModuleAfter(@Nullable Application application){
        for(String moduleApp: AppConfig.moduleApps){
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApp baseApp = (BaseApp) clazz.newInstance();
                baseApp.initModuleApp(application);
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            } catch (IllegalAccessException e){
                e.printStackTrace();
            } catch (InstantiationException e){
                e.printStackTrace();
            }
        }

    }

    private boolean isDebug(){
        return BuildConfig.DEBUG;
    }
}
