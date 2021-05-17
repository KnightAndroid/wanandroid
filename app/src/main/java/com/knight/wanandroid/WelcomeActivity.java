package com.knight.wanandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
        setTheme(getActivityTheme());

        mDatabind.logoAnim.addOffsetAnimListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();

            }
        });
        mDatabind.logoAnim.startAnimation();
//        XXPermissions.with(this)
//                .permission(Permission.CAMERA)
//                .request(new OnPermissionCallback() {
//                    @Override
//                    public void onGranted(List<String> permissions, boolean all) {
//                        if (all) {
//                            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//                            finish();
//                        }
//                    }
//                });

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
