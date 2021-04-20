package com.knight.wanandroid;

import android.content.Intent;
import android.os.Bundle;

import com.knight.wanandroid.databinding.ActivityWelcomeBinding;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_permiss.OnPermissionCallback;
import com.knight.wanandroid.library_permiss.Permission;
import com.knight.wanandroid.library_permiss.XXPermissions;

import java.util.List;

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
        setTheme(getActivityTheme());
        XXPermissions.with(this)
                .permission(Permission.CAMERA)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                        }
                    }
                });

    }

    @Override
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public int getActivityTheme(){
       return R.style.AppSplash;
    }
}
