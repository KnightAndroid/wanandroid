package com.knight.wanandroid.library_base.baseactivity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.loadsir.EmptyCallBack;
import com.knight.wanandroid.library_base.loadsir.ErrorCallBack;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.CreateUtils;
import com.knight.wanandroid.library_util.StatusBarUtils;
import com.knight.wanandroid.library_widget.loadcircleview.ProgressHUD;
import com.knight.wanandroid.library_widget.swipeback.SwipeBackHelper;

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
    protected int textColor;
    protected int bgColor;
    protected boolean isDarkMode;







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
        if (!isDarkMode) {
            initThemeColor();
            initTextColor();
            initBgColor();
        }

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
     * 获取字体颜色
     */
    protected void initTextColor() {
        textColor = CacheUtils.getInstance().getTextColor();
    }


    /**
     *
     * 获取背景颜色
     */
    protected void initBgColor() {
        bgColor = CacheUtils.getInstance().getBgThemeColor();

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
        super.onDestroy();
        mPresenter.onDettach();
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

}
