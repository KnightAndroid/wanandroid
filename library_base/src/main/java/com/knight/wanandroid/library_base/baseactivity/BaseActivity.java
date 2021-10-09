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
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.proxy.ProxyActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_util.CreateUtils;
import com.knight.wanandroid.library_util.LanguageFontSizeUtils;
import com.knight.wanandroid.library_util.StatusBarUtils;
import com.knight.wanandroid.library_widget.GrayFrameLayout;
import com.knight.wanandroid.library_widget.loadcircleview.ProgressHUD;
import com.knight.wanandroid.library_widget.swipeback.SwipeBackHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;

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
    private ProxyActivity mProxyActivity;
    protected int themeColor;
    protected boolean isDarkMode;
    protected boolean isEyeCare;

    /**
     *
     * 字体缩放大小
     *
     */
    private float fontScale = 1.0f;

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
     //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createViewDataBinding();
        StatusBarUtils.transparentStatusBar(this);
        isDarkMode = CacheUtils.getNormalDark();
        isEyeCare = CacheUtils.getIsEyeCare();

        initThemeColor();
        //此处try catch 因为如果自己和父类没有任何订阅方法会异常
        try {
            EventBus.getDefault().register(this);
        } catch (EventBusException ignored){
            // Subscriber class Activity and its super classes have no public methods with the @Subscribe annotation
        }
        setThemeColor(isDarkMode);
        initView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSwipeBackHelper = new SwipeBackHelper(this);
        }
        mProxyActivity = createProxyActivity();
        mProxyActivity.bindPresenter();
        mProxyActivity.initEye(isEyeCare);
        //内部获取第二个类型参数的真实类型，反射new出对象
        mPresenter = CreateUtils.get(this,1);
        //内部获取第三个类型参数的真实类型，反射new出对手
        mModel = CreateUtils.get(this,2);
        //使得p层绑定M层和V层，持有M和V的引用
        mPresenter.attachModelView(mModel,this);
        initData();

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
        return super.onCreateView(name, context, attrs);
    }

    /**
     * 获取主题颜色
     *
     */
    protected void initThemeColor() {
        themeColor = CacheUtils.getThemeColor();
    }

    /**
     *
     * 打开护眼模式
     */
    protected void openOrCloseEye(boolean status) {
        mProxyActivity.openOrCloseEye(status);
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
    public void onDestroy(){
        mPresenter.onDettach();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mProxyActivity.unbindPresenter();
        super.onDestroy();

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
        fontScale = CacheUtils.getSystemFontSize();

        super.attachBaseContext(LanguageFontSizeUtils.attachBaseContext(base,fontScale));
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

    @SuppressWarnings("unchecked")
    private ProxyActivity createProxyActivity() {
         if (mProxyActivity == null) {
             return new ProxyActivity(this);
         }
         return mProxyActivity;
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
