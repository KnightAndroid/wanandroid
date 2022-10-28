package com.knight.wanandroid.module_set.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetMessageManagerActivityBinding;

/**
 * Author:Knight
 * Time:2022/10/28 10:05
 * Description:PersonalAndManagerActivity
 */
@Route(path = RoutePathActivity.Set.Set_PersonalDeviceMessage)
public class PersonalAndManagerActivity extends BaseDBActivity<SetMessageManagerActivityBinding> {
    @Override
    public int layoutId() {
        return R.layout.set_message_manager_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        mDatabind.includeSetMessageManagerToobar.baseTvTitle.setText(getString(R.string.set_personal_message_manager));
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    public class ProxyClick {
        /**
         * 进入个人信息
         *
         */
        @LoginCheck
        public void goPersonalMessage() {
            ARouter.getInstance()
                    .build(RoutePathActivity.Set.Set_PersonalMessage)
                    .navigation();
        }
    }

}
