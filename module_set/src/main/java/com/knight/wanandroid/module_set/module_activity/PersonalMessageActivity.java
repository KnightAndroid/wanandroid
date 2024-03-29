package com.knight.wanandroid.module_set.module_activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.ServiceApiFactory;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetMessagePersonalActivityBinding;
import com.knight.wanandroid.module_set.module_external.MineExternalContact;

/**
 * Author:Knight
 * Time:2022/10/28 14:47
 * Description:PersonalMessageActivity
 */
@Route(path = RoutePathActivity.Set.Set_PersonalMessage)
public class PersonalMessageActivity extends BaseDBActivity<SetMessagePersonalActivityBinding> {
    @Override
    public int layoutId() {
        return R.layout.set_message_personal_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includePersonalMessageToolbar.baseTvTitle.setText(R.string.set_personal_message);
        mDatabind.tvUserName.setText(ModuleConfig.getInstance().user.getUsername());
        if (!TextUtils.isEmpty(ModuleConfig.getInstance().user.getEmail())) {
            mDatabind.tvUserEamil.setText(ModuleConfig.getInstance().user.getEmail());
        } else {
            mDatabind.tvUserEamil.setText("--");
        }

        mDatabind.tvCoinCount.setText(ModuleConfig.getInstance().user.getCoinCount()+"");
        MineExternalContact mineExternalContact = ServiceApiFactory.getInstance().getService(MineExternalContact.class);
        mDatabind.tvRank.setText(mineExternalContact.getUserRank());

        mDatabind.includePersonalMessageToolbar.baseIvBack.setOnClickListener(v ->{
            finish();
        });
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }
}
