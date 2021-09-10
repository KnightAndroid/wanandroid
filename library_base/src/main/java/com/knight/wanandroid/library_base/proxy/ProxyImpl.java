package com.knight.wanandroid.library_base.proxy;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.listener.NetworkStatusListener;
import com.knight.wanandroid.library_base.receiver.NetWorkChangeReceiver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/10 14:20
 * @descript:
 */
public class ProxyImpl implements IProxy, NetworkStatusListener,
        Application.ActivityLifecycleCallbacks {

    private Activity mActivity;
    private NetWorkChangeReceiver mNetWorkChangeReceiver;

    public ProxyImpl(Activity mActivity) {
        this.mActivity = mActivity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.mActivity.registerActivityLifecycleCallbacks(this);
        } else {
            this.mActivity.getApplication().registerActivityLifecycleCallbacks(this);
        }
    }

    /**
     *
     * 断网时弹出的View
     */
    private View tipView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    @Override
    public void bindPresenter() {
        //网络异常tip
        initTipView();
    }


    /**
     * 初始化网络异常提示View
     */
    private void initTipView() {
        tipView = mActivity.getLayoutInflater().inflate(R.layout.base_layout_network_tip, null);
        mWindowManager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }

    @Override
    public void unbindPresenter() {
        if (mActivity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mActivity.unregisterActivityLifecycleCallbacks(this);
        } else {
            mActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetWorkChangeReceiver = new NetWorkChangeReceiver();
        mNetWorkChangeReceiver.setNetworkStatusListener(this);
        mActivity.registerReceiver(mNetWorkChangeReceiver, intentFilter);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        if (mNetWorkChangeReceiver != null) {
            mActivity.unregisterReceiver(mNetWorkChangeReceiver);
            mNetWorkChangeReceiver = null;
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (tipView != null && tipView.getParent() != null) {
            mWindowManager.removeView(tipView);
            tipView = null;
        }
    }

    /**
     * 是否显示网络异常提示
     */
    protected boolean setShowNetWorkTip() {
        return true;
    }

    @Override
    public void onConnect() {
        if (setShowNetWorkTip()) {
            if (tipView != null && tipView.getParent() != null) {
                mWindowManager.removeView(tipView);
            }
        }
    }

    @Override
    public void disConnect() {
        if (setShowNetWorkTip()) {
            if (tipView.getParent() == null) {
                mWindowManager.addView(tipView, mLayoutParams);
            }
        }
    }
}
