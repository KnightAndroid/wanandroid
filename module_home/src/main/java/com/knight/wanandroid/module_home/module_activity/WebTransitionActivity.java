package com.knight.wanandroid.module_home.module_activity;

import android.os.Bundle;

import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeTransitionActivityBinding;

import androidx.core.view.ViewCompat;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/24 17:09
 * @descript:
 */

public class WebTransitionActivity extends BaseDBActivity<HomeTransitionActivityBinding> {


    @Override
    public int layoutId() {
        return R.layout.home_transition_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ViewCompat.setTransitionName(mDatabind.webImage, AppConfig.IMAGE_TRANSITION_NAME);
    }
}
