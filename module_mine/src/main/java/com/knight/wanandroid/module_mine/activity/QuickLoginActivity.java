package com.knight.wanandroid.module_mine.activity;

import android.os.Bundle;
import android.util.Base64;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.constant.MMkvConstants;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.DateUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_widget.GestureLockView;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.contract.QuickLoginContract;
import com.knight.wanandroid.module_mine.databinding.MineActivityQuickloginBinding;
import com.knight.wanandroid.module_mine.entity.LoginEntity;
import com.knight.wanandroid.module_mine.model.QuickLoginModel;
import com.knight.wanandroid.module_mine.presenter.QuickPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/22 17:16
 * @descript:快捷登录(包括指纹登录喝手势登录)
 */
@Route(path = RoutePathActivity.Mine.QuickLogin_Pager)
public final class QuickLoginActivity extends BaseActivity<MineActivityQuickloginBinding, QuickPresenter, QuickLoginModel> implements QuickLoginContract.QuickLoginView {

        GestureLockView.OnCheckPasswordListener onCheckPasswordListener = new GestureLockView.OnCheckPasswordListener() {
            @Override
            public boolean onCheckPassword(String passwd) {
                byte[] gesturePassword = Base64.decode(CacheUtils.getInstance().getGesturePassword(), Base64.URL_SAFE);
                return passwd.equals(new String(gesturePassword));
            }

            @Override
            public void onSuccess() {
                //调用登录接口
                String localLoginMessage = CacheUtils.getInstance().getLoginMessage();
                LoginEntity loginEntity = GsonUtils.get(localLoginMessage, LoginEntity.class);
                mPresenter.requestUserInfo(loginEntity.getLoginName(),loginEntity.getLoginPassword());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        };
    @Override
    public int layoutId() {
        return R.layout.mine_activity_quicklogin;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.mineTvGestureTime.setText(DateUtils.convertTime() + "~");
        mDatabind.includeQuickLoginToolbar.baseIvBack.setOnClickListener(v->finish());
        mDatabind.mineGesturelock.setOnCheckPasswordListener(onCheckPasswordListener);
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
    public void setUserInfo(UserInfoEntity userInfo) {
        //保存用户信息
        CacheUtils.getInstance().saveDataInfo(MMkvConstants.USER, userInfo);
        //登录成功发送事件
        EventBus.getDefault().post(new EventBusUtils.LoginInSuccess());
        finish();
    }
}
