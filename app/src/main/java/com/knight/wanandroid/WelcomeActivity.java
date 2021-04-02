package com.knight.wanandroid;

import android.content.Intent;
import android.os.Bundle;

import com.knight.wanandroid.databinding.ActivityWelcomeBinding;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/2 17:41
 * @descript:闪屏页
 */
public class WelcomeActivity extends BaseDBActivity<ActivityWelcomeBinding> {
    @Override
    public int layoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
