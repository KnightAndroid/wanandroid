package com.knight.wanandroid.module_mine.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.fragment.UpdateAppDialogFragment;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityAboutBinding;
import com.knight.wanandroid.module_mine.contract.AboutContract;
import com.knight.wanandroid.module_mine.model.AboutModel;
import com.knight.wanandroid.module_mine.presenter.AboutPresenter;

import androidx.annotation.RequiresApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/27 18:57
 * @descript:关于页面
 */
@Route(path = RoutePathActivity.Mine.About_Pager)
public final class AboutActivity extends BaseActivity<MineActivityAboutBinding, AboutPresenter, AboutModel> implements AboutContract.AboutView {


    private AppUpdateEntity mAppUpdateEntity;

    @Override
    public int layoutId() {
        return R.layout.mine_activity_about;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        mDatabind.mineTvAppName.setTextColor(themeColor);
        mDatabind.mineCv.setBackgroundColor(themeColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        mDatabind.mineIncludeAbout.baseTvTitle.setText(getString(R.string.mine_about_wanandroid));
        mDatabind.mineIncludeAbout.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.mineTvAppName.setText(SystemUtils.getAppName(this));
        mDatabind.mineTvAppVersion.setText(SystemUtils.getAppVersionName(this)+"("+SystemUtils.getAppVersionCode(this) +")");

    }

    @Override
    public void initData(){
        mPresenter.requestAppVersion();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.show(errorMsg);
    }

    @Override
    public void setAppVersion(AppUpdateEntity result) {
         if (!SystemUtils.getAppVersionName(this).equals(result.getVersionName())) {
             mDatabind.mineTvNewversion.setText(getString(R.string.mine_search_newversion));
             mAppUpdateEntity = result;
         }
    }

    public class ProxyClick{
        public void goUserAgree(){
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl","file:android_asset/wanandroid_useragree.html")
                    .withString("webTitle",getString(R.string.mine_service_protocol))
                    .navigation();
        }


        public void gouserprivacy(){
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl","file:android_asset/wanandroid_userprivacy.html")
                    .withString("webTitle",getString(R.string.mine_privacy_protocol))
                    .navigation();
        }

        public void goAppUpdateRecord(){
            startActivity(new Intent(AboutActivity.this,AppUpdateRecordActivity.class));
        }


        public void goParenterDirectory() {
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl","file:android_asset/access_partner_directory.html")
                    .withString("webTitle",getString(R.string.mine_access_partner_directory))
                    .navigation();
        }


        public void getCheckUpdate(){
            if (mAppUpdateEntity != null && !TextUtils.isEmpty(mAppUpdateEntity.getVersionName())) {
                if (!SystemUtils.getAppVersionName(AboutActivity.this).equals(mAppUpdateEntity.getVersionName())) {
                    UpdateAppDialogFragment.newInstance(mAppUpdateEntity).showAllowingStateLoss(getSupportFragmentManager(), "dialog_update");
                } else {
                    ToastUtils.show(R.string.mine_app_noupdate);
                }
            } else {
                ToastUtils.show(R.string.mine_app_noupdate);
            }



        }
    }
    

}
