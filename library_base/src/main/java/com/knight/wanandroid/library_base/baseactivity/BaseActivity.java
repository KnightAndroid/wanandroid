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
import com.knight.wanandroid.library_base.loadsir.EmptyCallBack;
import com.knight.wanandroid.library_base.loadsir.ErrorCallBack;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.receiver.NetWorkChangeReceiver;
import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_util.CreateUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.LanguageUtils;
import com.knight.wanandroid.library_util.StatusBarUtils;
import com.knight.wanandroid.library_widget.loadcircleview.ProgressHUD;
import com.knight.wanandroid.library_widget.swipeback.SwipeBackHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 19:54
 * @descript:Activity基类
 */
public abstract class BaseActivity<DB extends ViewDataBinding,T extends BasePresenter,M extends BaseModel> extends AppCompatActivity implements OnHttpListener {

    public abstract int layoutId();

    public DB mDatabind;
    public T mPresenter;
    public M mModel;

    public LoadService mLoadService;
    private ProgressHUD mProgressHUD;

    private SwipeBackHelper mSwipeBackHelper;
    protected int themeColor;
    protected int bgColor;
    protected boolean isDarkMode;
    protected boolean isEyeCare;

    //没网络监听提示的view
    protected View tipView;
    protected WindowManager mWindowManager;
    protected WindowManager.LayoutParams mLayoutParams;
    protected NetWorkChangeReceiver mNetWorkChangeReceiver;

    //护眼模式遮罩
    private FrameLayout meyeFrameLayout;


    /**
     * 主题色设置
     *
     */
    protected abstract void setThemeColor(boolean isDarkMode);



    public abstract void initView(Bundle savedInstanceState);


    protected void initData(){}

    protected void reLoadData(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getActivityTheme());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createViewDataBinding();
        StatusBarUtils.transparentStatusBar(this);
        isDarkMode = CacheUtils.getInstance().getNormalDark();
        isEyeCare = CacheUtils.getInstance().getIsEyeCare();
        initThemeColor();
        EventBus.getDefault().register(this);
        initTipView();
        initEye();
        setThemeColor(isDarkMode);
        initView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSwipeBackHelper = new SwipeBackHelper(this);
        }
        //内部获取第二个类型参数的真实类型，反射new出对象
        mPresenter = CreateUtils.get(this,1);
        //内部获取第三个类型参数的真实类型，反射new出对手
        mModel = CreateUtils.get(this,2);
        //使得p层绑定M层和V层，持有M和V的引用
        mPresenter.attachModelView(mModel,this);
        initData();


    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetWorkChangeReceiver = new NetWorkChangeReceiver();
        registerReceiver(mNetWorkChangeReceiver,intentFilter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetWorkState(EventBusUtils.NetWorkState netWorkState) {
        if (netWorkState.isConnected()) {
            reLoadData();
            if (tipView != null && tipView.getParent() != null) {
                mWindowManager.removeView(tipView);
            }
        } else {
            if (tipView.getParent() == null) {
                mWindowManager.addView(tipView,mLayoutParams);
            }
        }

    }




    /**
     *
     * 获取主题颜色
     *
     */
    protected void initThemeColor() {
        themeColor = CacheUtils.getInstance().getThemeColor();
    }


    /**
     *
     * 初始化网络链接提示view
     */
    private void initTipView() {
        tipView = getLayoutInflater().inflate(R.layout.base_layout_network_tip,null);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }


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
     *
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


    /**
     *
     * 显示加载进度布局
     * @param view
     */
    public void showLoading(View view){
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mLoadService.showCallback(LoadCallBack.class);
                reLoadData();
            }
        });


    }

    /**
     *
     * 成功请求数据
     */
    protected void showSuccess() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }


    /**
     *
     * 失败请求的界面
     */
    protected void showFailure() {
        if (mLoadService != null) {
            mLoadService.showCallback(ErrorCallBack.class);
        }
    }

    /**
     *
     * 空数据界面
     */
    protected void showEmptyData(){
        if (mLoadService != null) {
            mLoadService.showCallback(EmptyCallBack.class);

        }
    }


    private void createViewDataBinding() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId());
        mDatabind.setLifecycleOwner(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mNetWorkChangeReceiver != null) {
            unregisterReceiver(mNetWorkChangeReceiver);
            mNetWorkChangeReceiver = null;
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDettach();
        EventBus.getDefault().unregister(this);
        if (tipView != null && tipView.getParent() != null) {
            mWindowManager.removeView(tipView);
        }
    }

    /**
     * 显示请求框
     * @param loadMessage
     */
    public void showLoadingHud(String loadMessage) {
        if (mProgressHUD == null) {
            mProgressHUD = new ProgressHUD(this,loadMessage);
        }
        mProgressHUD.show();
    }

    /**
     *
     * 隐藏请求框
     *
     */
    public void dismissLoadingHud() {
        if (mProgressHUD != null) {
            mProgressHUD.dismiss();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageUtils.attachBaseContext(base));
    }



    protected int getActivityTheme(){
        return R.style.base_AppTheme;
    }



    @Override
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

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
