package com.knight.wanandroid.library_base.baseactivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.loadsir.EmptyCallBack;
import com.knight.wanandroid.library_base.loadsir.ErrorCallBack;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_base.proxy.ProxyActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_util.LanguageFontSizeUtils;
import com.knight.wanandroid.library_util.StatusBarUtils;
import com.knight.wanandroid.library_widget.GrayFrameLayout;
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
public abstract class BaseDBActivity<DB extends ViewDataBinding> extends AppCompatActivity implements OnHttpListener {

    /**
     * 返回布局文件
     *
     * @return
     */
    public abstract int layoutId();

    public DB mDatabind;
    private ProgressHUD mProgressHUD;
    private ProxyActivity mProxyActivity;

    public LoadService mLoadService;

    private SwipeBackHelper mSwipeBackHelper;

    protected int themeColor;
    protected boolean isDarkMode;
    /**
     *
     * 当前模式是否遮罩
     *
     */
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
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createViewDataBinding();
        StatusBarUtils.transparentStatusBar(this);
        isDarkMode = CacheUtils.getNormalDark();
        isEyeCare = CacheUtils.getIsEyeCare();
        initThemeColor();
        setThemeColor(isDarkMode);
        initView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSwipeBackHelper = new SwipeBackHelper(this);
        }

        mProxyActivity = createProxyActivity();
        mProxyActivity.bindPresenter();
        mProxyActivity.initEye(isEyeCare);

    }

    @SuppressWarnings("unchecked")
    private ProxyActivity createProxyActivity() {
        if (mProxyActivity == null) {
            return new ProxyActivity(this);
        }
        return mProxyActivity;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if(AppConfig.gray && "FrameLayout".equals(name)){
            int count = attrs.getAttributeCount();
            for (int i = 0; i < count; i++) {
                String attributeName = attrs.getAttributeName(i);
                String attributeValue = attrs.getAttributeValue(i);
                if (attributeName.equals("id")) {
                    int id = Integer.parseInt(attributeValue.substring(1));
                    String idVal = getResources().getResourceName(id);
                    if ("android:id/content".equals(idVal)) {
                        GrayFrameLayout grayFrameLayout = new GrayFrameLayout(context, attrs);
                        return grayFrameLayout;
                    }
                }
            }
        }
//        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//            // View view = super.onCreateView(name, context, attrs);
//            LandRootFrameLayout landRootFrameLayout = new LandRootFrameLayout(context, attrs);
//            // view.setPadding(StatusBarUtils.getStatusBarHeight(context),0,0,0);
//            return landRootFrameLayout;
//        }
        return super.onCreateView(name, context, attrs);
    }



    /**
     * 打开护眼模式
     */
    protected void openOrCloseEye(boolean status) {
        mProxyActivity.openOrCloseEye(status);
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
        themeColor = CacheUtils.getThemeColor();
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
        fontScale = CacheUtils.getSystemFontSize();
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
    public void onDestroy() {
        mProxyActivity.unbindPresenter();
        super.onDestroy();
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
