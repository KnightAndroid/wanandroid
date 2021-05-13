package com.knight.wanandroid.library_base.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_util.StatusBarUtils;
import com.knight.wanandroid.library_widget.loadcircleview.ProgressHUD;

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

    public abstract int layoutId();
    public DB mDatabind;
    private ProgressHUD mProgressHUD;

    public abstract void initView(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getActivityTheme());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createViewDataBinding();
        StatusBarUtils.transparentStatusBar(this);
        initView(savedInstanceState);
    }


    private void createViewDataBinding() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId());
        mDatabind.setLifecycleOwner(this);
    }


    protected int getActivityTheme(){
        return R.style.base_AppTheme;
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
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

    }

}
