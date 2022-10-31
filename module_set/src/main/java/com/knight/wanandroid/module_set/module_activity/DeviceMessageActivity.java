package com.knight.wanandroid.module_set.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetDeviceMessageActivityBinding;
import com.knight.wanandroid.module_set.module_util.DeviceUtils;

/**
 * Author:Knight
 * Time:2022/10/31 9:28
 * Description:DeviceMessageActivity
 */
@Route(path = RoutePathActivity.Set.Set_DeViceMessage)
public class DeviceMessageActivity extends BaseDBActivity<SetDeviceMessageActivityBinding> {
    @Override
    public int layoutId() {
        return R.layout.set_device_message_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.tvDeviceSystemVersion.setText(DeviceUtils.getSystemVersion());
        mDatabind.tvAndroidSdkVersion.setText(DeviceUtils.getAndroidSdkVersion()+"");
        mDatabind.tvScreenSize.setText(ScreenUtils.getScreenHeight(this) + "x" + ScreenUtils.getScreenWidth(this));
        mDatabind.tvArea.setText(DeviceUtils.getCountry());
        mDatabind.tvTimeZone.setText(DeviceUtils.getTimeZone());
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }
}
