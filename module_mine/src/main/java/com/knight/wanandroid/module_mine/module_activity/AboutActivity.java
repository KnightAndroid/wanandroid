package com.knight.wanandroid.module_mine.module_activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityAboutBinding;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import androidx.annotation.RequiresApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/27 18:57
 * @descript:
 */
@Route(path = RoutePathActivity.Mine.About_Pager)
public class AboutActivity extends BaseDBActivity<MineActivityAboutBinding> {
    @Override
    public int layoutId() {
        return R.layout.mine_activity_about;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        mDatabind.mineIncludeAbout.baseTvTitle.setText(getString(R.string.mine_about_wanandroid));
        mDatabind.mineIncludeAbout.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.mineTvAppName.setText(SystemUtils.getAppName(this));
        mDatabind.mineTvAppVersion.setText(SystemUtils.getAppVersionName(this)+"("+SystemUtils.getAppVersionCode(this) +")");
        getUpgradeInfo();
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


        public void getCheckUpdate(){
            /***** 检查更新 *****/
            if (TextUtils.isEmpty(mDatabind.mineTvNewversion.getText().toString())) {
                ToastUtils.getInstance().showToast("已安装最新版本");
            } else {
                Beta.checkUpgrade();
            }

        }
    }
    

    
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void getUpgradeInfo(){
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();

        if (upgradeInfo == null) {
            return;
        }

        if (upgradeInfo.versionCode != SystemUtils.getAppVersionCode(this)) {
            mDatabind.mineTvNewversion.setText(getString(R.string.mine_search_newversion));
        } else {
            mDatabind.mineTvNewversion.setText("");
        }
    }
}
