package com.knight.wanandroid.library_base.baseactivity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.listener.NetworkStatusListener;
import com.knight.wanandroid.library_base.loadsir.EmptyCallBack;
import com.knight.wanandroid.library_base.loadsir.ErrorCallBack;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_base.receiver.NetWorkChangeReceiver;
import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_common.utils.ColorUtils;
import com.knight.wanandroid.library_util.LanguageFontSizeUtils;
import com.knight.wanandroid.library_util.StatusBarUtils;
import com.knight.wanandroid.library_widget.loadcircleview.ProgressHUD;
import com.knight.wanandroid.library_widget.swipeback.SwipeBackHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/2 17:25
 * @descript:非业务界面
 */
public abstract class BaseDBActivity<DB extends ViewDataBinding> extends AppCompatActivity implements OnHttpListener,
        NetworkStatusListener {

    /**
     * 返回布局文件
     *
     * @return
     */
    public abstract int layoutId();

    public DB mDatabind;
    private ProgressHUD mProgressHUD;


    public LoadService mLoadService;

    private SwipeBackHelper mSwipeBackHelper;

    protected int themeColor;
    protected boolean isDarkMode;

    /**
     * 没网络监听提示的view
     */
    private View tipView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private NetWorkChangeReceiver mNetWorkChangeReceiver;


    /**
     * 护眼模式遮罩
     */
    private FrameLayout meyeFrameLayout;
    protected boolean isEyeCare;

    /**
     *
     * 字体缩放大小
     *
     */
    private float fontScale = 1.0f;


    public abstract void initView(Bundle savedInstanceState);


    /**
     * 主题色设置
     */
    protected abstract void setThemeColor(boolean isDarkMode);


    /**
     * 是否显示网络异常提示
     */
    protected boolean setShowNetWorkTip() {
        return true;
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 重新读取数据
     */
    protected void reLoadData() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getActivityTheme());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createViewDataBinding();
        StatusBarUtils.transparentStatusBar(this);
        isDarkMode = CacheUtils.getInstance().getNormalDark();
        isEyeCare = CacheUtils.getInstance().getIsEyeCare();

        initTipView();
        initEye();
        initThemeColor();
        setThemeColor(isDarkMode);
        initView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSwipeBackHelper = new SwipeBackHelper(this);
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetWorkChangeReceiver = new NetWorkChangeReceiver();
        mNetWorkChangeReceiver.setNetworkStatusListener(this);
        registerReceiver(mNetWorkChangeReceiver, intentFilter);
    }

    /**
     * 初始化网络异常提示View
     */
    private void initTipView() {
        tipView = getLayoutInflater().inflate(R.layout.base_layout_network_tip, null);
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }


    /**
     * 初始化护眼模式View
     */
    private void initEye() {
        meyeFrameLayout = new FrameLayout(this);
        openOrCloseEye(isEyeCare);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().addContentView(meyeFrameLayout, params);
    }


    /**
     * 打开护眼模式
     */
    protected void openOrCloseEye(boolean status) {
        if (status) {
            if (meyeFrameLayout != null) {
                meyeFrameLayout.setBackgroundColor(ColorUtils.getFilterColor(70));
            }
        } else {
            if (meyeFrameLayout != null) {
                meyeFrameLayout.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }


    private void createViewDataBinding() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId());
        mDatabind.setLifecycleOwner(this);
    }

    /**
     * 设置页面主题
     *
     * @return
     */
    protected int getActivityTheme() {
        return R.style.base_AppTheme;
    }


    /**
     * 获取颜色
     */
    protected void initThemeColor() {
        themeColor = CacheUtils.getInstance().getThemeColor();
    }


    /**
     * 显示加载进度布局
     *
     * @param view
     */
    public void showLoading(View view) {
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mLoadService.showCallback(LoadCallBack.class);
                reLoadData();
            }
        });


    }

    /**
     * 成功请求数据
     */
    protected void showSuccess() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }


    /**
     * 失败请求的界面
     */
    protected void showFailure() {
        if (mLoadService != null) {
            mLoadService.showCallback(ErrorCallBack.class);
        }
    }

    /**
     * 空数据界面
     */
    protected void showEmptyData() {
        if (mLoadService != null) {
            mLoadService.showCallback(EmptyCallBack.class);

        }
    }

    /**
     * 显示请求框
     *
     * @param loadMessage
     */
    public void showLoadingHud(String loadMessage) {
        if (mProgressHUD == null) {
            mProgressHUD = new ProgressHUD(this, loadMessage);
        }
        mProgressHUD.show();
    }

    /**
     * 隐藏请求框
     */
    public void dismissLoadingHud() {
        if (mProgressHUD != null) {
            mProgressHUD.dismiss();
        }
    }


    /**
     *
     * {@link OnHttpListener}
     * @param result
     */
    @Override
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    protected void attachBaseContext(Context base) {
        fontScale = CacheUtils.getInstance().getSystemFontSize();
        super.attachBaseContext(LanguageFontSizeUtils.attachBaseContext(base,fontScale));
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mSwipeBackHelper != null && mSwipeBackHelper.dispatchTouchEvent(event)) {
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onPause() {
        if (mNetWorkChangeReceiver != null) {
            unregisterReceiver(mNetWorkChangeReceiver);
            mNetWorkChangeReceiver = null;
        }
        super.onPause();
    }



    @Override
    public void onDestroy() {
        if (tipView != null && tipView.getParent() != null) {
            mWindowManager.removeView(tipView);
            tipView = null;
        }

        super.onDestroy();


    }

    /**
     * 已经连接
     */
    @Override
    public void onConnect() {
        if (setShowNetWorkTip()) {
            reLoadData();
            if (tipView != null && tipView.getParent() != null) {
                mWindowManager.removeView(tipView);
            }
        }
    }

    /**
     *
     * 断开连接
     */
    @Override
    public void disConnect() {
        if (setShowNetWorkTip()) {
            if (tipView.getParent() == null) {
                mWindowManager.addView(tipView, mLayoutParams);
            }
        }

    }

//    @Override
//    public Resources getResources() {
//        Resources resources = super.getResources();
//        return LanguageFontSizeUtils.getResources(this, resources, fontScale);
//    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        // 兼容androidX在部分手机切换语言失败问题
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);

    }



}
