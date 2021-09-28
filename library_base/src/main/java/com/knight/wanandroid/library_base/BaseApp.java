package com.knight.wanandroid.library_base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.knight.wanandroid.library_base.constants.BaseConstants;
import com.knight.wanandroid.library_base.util.HookUtils;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.SystemUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 18:49
 * @descript:描述BaseApp
 */
public abstract class BaseApp extends Application {

    private boolean userAgree;
    private static BaseApp app;
    String checkActivityName = null;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //mmkv初始化
        CacheUtils.init(base);
        userAgree = CacheUtils.getAgreeStatus();
        checkActivityName = SystemUtils.getCheckActivityName(base,BaseConstants.KEY_CHECK_ACTIVITY);
        if (!userAgree) {
            //只有在用户不同意的情况下才hook ，避免性能损失
            try {
                HookUtils.attachContext(checkActivityName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!SystemUtils.isRunOnMainProcess(this)) {
            return;
        }
        app = this;
        initSafeSDK();
        //初始化那些和隐私无关的SDK
        if (userAgree) {
            initDangerousSDK();
        }

    }


    public static BaseApp getApp() {
        return app;
    }


    /**
     * 初始化那些和用户隐私无关的SDK
     * 如果无法区分，建议只使用initSDK一个方法
     */
    protected abstract void initSafeSDK();

    /**
     * 子类重写用于初始化危险SDK等相关工作
     */

    abstract protected void initDangerousSDK();

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


    /**
     *
     * 此方法是为了新增隐私(流程)界面 不影响原来流程所设置方法，暂时不去掉
     * @param activity
     * @param gotoFirstActivity
     * @param extras
     */
    public void agree(Activity activity, boolean gotoFirstActivity, Bundle extras) {
        HookUtils.initProvider(this);
        initDangerousSDK();
        //启动真正的启动页
        if (!gotoFirstActivity) {
            //已经是同一个界面了，不需要自动打开
            return;
        }
        try {
            Intent intent = new Intent(activity, Class.forName(SystemUtils.getRealFirstActivityName()));
            if (extras != null) {
                intent.putExtras(extras);//也许是从网页中调起app，这时候extras中含有打开特定新闻的参数。需要传递给真正的启动页
            }
            activity.startActivity(intent);
            activity.finish();//关闭当前页面
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * 相当于回调
     * 同意隐私之后再初始化
     * @param
     * @param
     * @param
     */
    public void agreeInitSdk() {
        //初始化Provider
        HookUtils.initProvider(this);
        initDangerousSDK();
        //启动真正的启动页
    }




}
