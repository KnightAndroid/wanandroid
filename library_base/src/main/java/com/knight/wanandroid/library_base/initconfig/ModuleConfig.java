package com.knight.wanandroid.library_base.initconfig;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.kingja.loadsir.core.LoadSir;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.BuildConfig;
import com.knight.wanandroid.library_base.interceptor.PermissionInterceptor;
import com.knight.wanandroid.library_base.loadsir.EmptyCallBack;
import com.knight.wanandroid.library_base.loadsir.ErrorCallBack;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_network.HttpConfig;
import com.knight.wanandroid.library_network.config.IRequestInterceptor;
import com.knight.wanandroid.library_network.config.IRequestServer;
import com.knight.wanandroid.library_network.data.HttpHeaders;
import com.knight.wanandroid.library_network.data.HttpParams;
import com.knight.wanandroid.library_network.model.RequestHandler;
import com.knight.wanandroid.library_network.server.ReleaseServer;
import com.knight.wanandroid.library_permiss.XXPermissions;
import com.knight.wanandroid.library_util.ToastUtils;
import com.tencent.mmkv.MMKV;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 19:50
 * @descript:
 */
public class ModuleConfig {



    private ClearableCookieJar mCookieJar;
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
        //状态页
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallBack())
                .addCallback(new LoadCallBack())
                .addCallback(new EmptyCallBack())
                .setDefaultCallback(LoadCallBack.class) //默认状态页
                .commit();

        //网络请求初始化
        initOkhttp(application);
        //mmkv初始化
        MMKV.initialize(application);




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


    private void initOkhttp(Application application) {
        mCookieJar = new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(application));
        IRequestServer server = new ReleaseServer();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(60,TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(60,TimeUnit.SECONDS)//设置写入超时时间
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接
                .cookieJar(mCookieJar)
                .build();
        HttpConfig.with(okHttpClient)
                .setLogEnabled(BuildConfig.DEBUG)
                .setServer(server)
                .setHandler(new RequestHandler(application))
                .setInterceptor(new IRequestInterceptor() {
                    @Override
                    public void intercept(String url, String tag, HttpParams params, HttpHeaders headers) {

                    }
                })
                .setRetryCount(1)
                .setRetryTime(1000)
                .into();
    }
}
