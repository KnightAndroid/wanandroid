package com.knight.wanandroid.library_base.activity;

import android.os.Bundle;

import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_util.StatusBarUtils;

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

    public abstract void initView(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createViewDataBinding();
        StatusBarUtils.transparentStatusBar(this);
        initView(savedInstanceState);
    }


    private void createViewDataBinding() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId());
        mDatabind.setLifecycleOwner(this);
    }

}
