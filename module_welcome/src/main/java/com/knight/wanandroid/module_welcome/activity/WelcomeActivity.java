package com.knight.wanandroid.module_welcome.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_common.utils.ColorUtils;
import com.knight.wanandroid.module_welcome.R;
import com.knight.wanandroid.module_welcome.contract.WelcomeContract;
import com.knight.wanandroid.module_welcome.databinding.WelcomeActivityBinding;
import com.knight.wanandroid.module_welcome.entity.AppThemeEntity;
import com.knight.wanandroid.module_welcome.fragment.WelcomePrivacyAgreeFragment;
import com.knight.wanandroid.module_welcome.model.WelcomeModel;
import com.knight.wanandroid.module_welcome.presenter.WelcomePresenter;

public class WelcomeActivity extends BaseActivity<WelcomeActivityBinding, WelcomePresenter, WelcomeModel> implements WelcomeContract.WelcomeView {


    @Override
    public int layoutId() {
        return R.layout.welcome_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setTheme(getActivityTheme());
        mDatabind.logoAnim.addOffsetAnimListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (CacheUtils.getAgreeStatus()) {
                    ARouter.getInstance().build(RoutePathActivity.Main.MainPager)
                            .navigation();
                    finish();
                } else {
                    new WelcomePrivacyAgreeFragment().show(getSupportFragmentManager(), "dialog_privacy");
                }
            }
        });
        mDatabind.logoAnim.startAnimation();
    }


    @Override
    public void initData(){
        mPresenter.requestThemeColor();
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public int getActivityTheme() {
        return R.style.welcomeAppSplash;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void setThemeColor(AppThemeEntity appThemeEntity) {
        AppConfig.appThemeColor = appThemeEntity.getThemeColor();
        if (appThemeEntity.isForceTheme()) {
            CacheUtils.setThemeColor(ColorUtils.convertToColorInt(AppConfig.appThemeColor));
        }
        AppConfig.gray = appThemeEntity.isGray();
    }
}