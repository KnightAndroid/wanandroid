package com.knight.wanandroid.library_base.initconfig;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.kingja.loadsir.core.LoadSir;
import com.knight.wanandroid.library_aop.loginintercept.ILoginFilter;
import com.knight.wanandroid.library_aop.loginintercept.LoginManager;
import com.knight.wanandroid.library_base.BuildConfig;
import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.interceptor.PermissionInterceptor;
import com.knight.wanandroid.library_base.loadsir.EmptyCallBack;
import com.knight.wanandroid.library_base.loadsir.ErrorCallBack;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_base.util.AppInitTools;
import com.knight.wanandroid.library_common.constant.MMkvConstants;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_network.HttpConfig;
import com.knight.wanandroid.library_network.config.IRequestApi;
import com.knight.wanandroid.library_network.config.IRequestInterceptor;
import com.knight.wanandroid.library_network.config.IRequestServer;
import com.knight.wanandroid.library_network.data.HttpHeaders;
import com.knight.wanandroid.library_network.data.HttpParams;
import com.knight.wanandroid.library_network.interceptor.BaseUrlInterceptor;
import com.knight.wanandroid.library_network.interceptor.CacheInterceptor;
import com.knight.wanandroid.library_network.model.RequestHandler;
import com.knight.wanandroid.library_network.server.ReleaseServer;
import com.knight.wanandroid.library_permiss.XXPermissions;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.toast.ToastInterceptor;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.wanandroid.knight.library_database.mananger.DataBaseManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 19:50
 * @descript:
 */
public class ModuleConfig {

    public UserInfoEntity user = null;

    private ModuleConfig(){

    }

    private static class SingleHolder{
        private static ModuleConfig instance = new ModuleConfig();
    }

    public static ModuleConfig getInstance(){
        return SingleHolder.instance;
    }





    public void initSafeSdk(@Nullable Application application) {
        //初始化ARouter
        if(isDebug()){
            //如果是debug模式
            ARouter.openLog();
            ARouter.openDebug();
        }

        //初始化路由
        ARouter.init(application);
        //初始化Toast
        ToastUtils.setInterceptor(new ToastInterceptor());
        ToastUtils.init(application);
        //网络请求初始化
        initOkhttp(application);

        //登录拦截器
        initLoginFilter(application);
        //初始化数据库
        DataBaseManager.getDataBase(application,"wanandroid_database");
        //初始化用户信息
        user = initUser();
        //状态页
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallBack())
                .addCallback(new LoadCallBack())
                .addCallback(new EmptyCallBack())
                //默认状态页
                .setDefaultCallback(LoadCallBack.class)
                .commit();
        SystemUtils.darkNormal();
    }


    public void initDangerousSDK(@Nullable Application application) {
        //初始化权限拦截器
        XXPermissions.setPermissionInterceptor(new PermissionInterceptor());
        //bugly异常上报
        CrashReport.initCrashReport(application, "669abbf2c8", false);
    }


    /**
     *
     * 后面初始化
     * @param application
     */
    public void initModuleAfter(@Nullable Application application){
        AppInitTools.getInstance().initAllModuelApplication(application);
    }

    private boolean isDebug(){
        return BuildConfig.DEBUG;
    }


    /**
     *
     * 初始化okhttp
     * @param application
     */
    private void initOkhttp(Application application) {
        //缓存目录
        File cacheFile = new File(application.getCacheDir(), "knight_wanandroid");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        IRequestServer server = new ReleaseServer();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //设置读取超时时间
                .readTimeout(60, TimeUnit.SECONDS)
                //设置请求超时时间
                .connectTimeout(60,TimeUnit.SECONDS)
                //设置写入超时时间
                .writeTimeout(60,TimeUnit.SECONDS)
                //设置出现错误进行重新连接
                .retryOnConnectionFailure(true)
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new CacheInterceptor())
                .cache(cache)
                .cookieJar(new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(application)))
                .build();
        HttpConfig.with(okHttpClient)
                .setLogEnabled(BuildConfig.DEBUG)
                .setServer(server)
                .setHandler(new RequestHandler(application))
                // 设置请求参数拦截器
                .setInterceptor(new IRequestInterceptor() {
                    @Override
                    public void interceptArguments(IRequestApi api, HttpParams params, HttpHeaders headers) {
                        headers.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    }
                })
                .setRetryCount(1)
                .setRetryTime(1000)
                .into();
    }

    /**
     *
     *
     * @param application
     */
    private void initLoginFilter(Application application){
        ILoginFilter iLoginFilter = new ILoginFilter() {
            @Override
            public void login(Context applicationContext, boolean loginDefine) {
                //没跳过登录
                if(!loginDefine){
                    ToastUtils.show(R.string.base_login_first);
                }
            }

            @Override
            public boolean isLogin(Context applicationContext) {
                UserInfoEntity userInfoEntity = CacheUtils.getDataInfo(MMkvConstants.USER, UserInfoEntity.class);
                if (userInfoEntity != null) {
                    return true;
                } else {
                    return false;
                }


            }

            @Override
            public void clearLoginStatus(Context applicationContext) {

            }
        };
        LoginManager.getInstance().init(application,iLoginFilter);
    }

    /**
     * 初始化用户信息
     * @return
     */
    private UserInfoEntity initUser(){
        UserInfoEntity userInfoEntity = CacheUtils.getDataInfo(MMkvConstants.USER,UserInfoEntity.class);
        return userInfoEntity;
    }


}
