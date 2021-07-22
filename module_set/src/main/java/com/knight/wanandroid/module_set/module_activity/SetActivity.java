package com.knight.wanandroid.module_set.module_activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.dialog.DialogUtils;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetActivityBinding;
import com.knight.wanandroid.module_set.module_contract.SetContract;
import com.knight.wanandroid.module_set.module_model.SetModel;
import com.knight.wanandroid.module_set.module_presenter.SetPresenter;

import org.greenrobot.eventbus.EventBus;


@Route(path = RoutePathActivity.Set.Set_pager)
public class SetActivity extends BaseActivity<SetActivityBinding, SetPresenter, SetModel> implements SetContract.SetView {


    @Override
    public int layoutId() {
        return R.layout.set_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        mDatabind.includeSetToobar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includeSetToobar.baseTvTitle.setText(getString(R.string.set_app_name));
        if (ModuleConfig.getInstance().user != null) {
            mDatabind.setRlLogout.setVisibility(View.VISIBLE);
        } else {
            mDatabind.setRlLogout.setVisibility(View.GONE);
        }
        initDarkMode();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Override
    public void logoutSuccess() {
        mDatabind.setRlLogout.setVisibility(View.GONE);
        //退出登录成功
        CacheUtils.getInstance().loginOut();
        ModuleConfig.getInstance().user = null;
        EventBus.getDefault().post(new EventBusUtils.LogoutSuccess());
    }


    public class ProxyClick {
        public void projectRepository() {
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl", "https://github.com/KnightAndroid/wanandroid")
                    .withString("webTitle", getString(R.string.set_project_repository))
                    .navigation();
        }

        public void goOfficialWebsite() {
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl", "https://www.wanandroid.com/")
                    .withString("webTitle", getString(R.string.set_official_website))
                    .navigation();
        }

        public void Logout() {
            DialogUtils.getConfirmDialog(SetActivity.this, getResources().getString(R.string.set_confirm_logout), (dialog, which) -> {
                mPresenter.requestLogout();
            }, (dialog, which) -> {

            }).show();

        }

        public void goAbout() {
            ARouter.getInstance().build(RoutePathActivity.Mine.About_Pager)
                    .navigation();
        }

        public void goSelectDarkMode() {
            ARouter.getInstance().build(RoutePathActivity.Set.Set_DarkMode)
                    .navigation();
        }

    }

    /**
     *
     *  初始化颜色
     */
    private void initDarkMode() {
        if (CacheUtils.getInstance().getFollowSystem()) {
            mDatabind.setDarkmodeStatus.setText(getString(R.string.set_follow_system));
        } else {
            if (CacheUtils.getInstance().getNormalDark()) {
               mDatabind.setDarkmodeStatus.setText(getString(R.string.set_dark_open));
            } else {
                mDatabind.setDarkmodeStatus.setText(getString(R.string.set_dark_close));
            }
        }
    }

}