package com.knight.wanandroid.library_base.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/2 17:25
 * @descript:非业务界面
 */
public abstract class BaseDBActivity<DB extends ViewDataBinding> extends AppCompatActivity {

    public abstract int layoutId();
    public DB mDatabind;

    public abstract void initView(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createViewDataBinding();
        initView(savedInstanceState);
    }


    private void createViewDataBinding() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId());
        mDatabind.setLifecycleOwner(this);
    }

}
