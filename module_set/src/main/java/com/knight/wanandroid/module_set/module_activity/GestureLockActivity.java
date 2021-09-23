package com.knight.wanandroid.module_set.module_activity;

import android.os.Bundle;
import android.util.Base64;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_widget.GestureLockView;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetGesturelockActivityBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/22 14:49
 * @descript:
 */

@Route(path = RoutePathActivity.Set.Set_GestureLock)
public final class GestureLockActivity extends BaseDBActivity<SetGesturelockActivityBinding> {
    private String oneGesturePassword;
    GestureLockView.OnSetPasswordListener mOnSetPasswordListener = new GestureLockView.OnSetPasswordListener() {
        @Override
        public void setOneGesturePassword(String onePassword) {
            oneGesturePassword = onePassword;

        }

        @Override
        public void setTwoGesturePassword(String twoPassword) {
            CacheUtils.setGesturePassword(Base64.encodeToString(twoPassword.getBytes(), Base64.URL_SAFE));
            //设置手势登录
            CacheUtils.setGestureLogin(true);
            finish();
        }

        @Override
        public boolean setOnCheckPassword(String passwd) {
            return oneGesturePassword.equals(passwd);
        }

        @Override
        public void setError(String errorMsg) {
            ToastUtils.show(errorMsg);

        }
    };

    @Override
    public int layoutId() {
        return R.layout.set_gesturelock_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.customGestureLockView.setDotPressedColor(themeColor);
        mDatabind.customGestureLockView.setLineColor(themeColor);
        mDatabind.customGestureLockView.setPasswordListener(mOnSetPasswordListener);
        mDatabind.includeGestureToolbar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includeGestureToolbar.baseTvTitle.setText(R.string.set_show_gesture_password);
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }
}
