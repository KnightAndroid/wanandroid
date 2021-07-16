package com.knight.wanandroid.module_set.module_activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetActivityBinding;


@Route(path = RoutePathActivity.Set.Set_pager)
public class SetActivity extends BaseDBActivity<SetActivityBinding> {


    @Override
    public int layoutId() {
        return R.layout.set_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
      //  EventBus.getDefault().register(this);
        mDatabind.setClick(new ProxyClick());
        if (ModuleConfig.getInstance().user != null){
            mDatabind.setRlLogout.setVisibility(View.VISIBLE);
        } else {
            mDatabind.setRlLogout.setVisibility(View.GONE);
        }

    }


    public class ProxyClick{
        public void projectRepository(){
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl","https://github.com/KnightAndroid/wanandroid")
                    .withString("webTitle",getString(R.string.set_project_repository))
                    .navigation();
        }

        public void goOfficialWebsite(){
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl","https://www.wanandroid.com/")
                    .withString("webTitle",getString(R.string.set_official_website))
                    .navigation();
        }
        public void Logout(){
//            DialogUtils.getConfirmDialog(SetActivity.this, getResources().getString(R.string.set_confirm_logout), (dialog, which) -> {
//                mPresenter.requestLogout();
//            }, (dialog, which) -> {
//
//            }).show();

        }

        public void goAbout(){
            ARouter.getInstance().build(RoutePathActivity.Mine.About_Pager)
                    .navigation();
        }

    }

//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
}