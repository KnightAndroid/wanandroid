package com.knight.wanandroid.module_mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_biometric.control.BiometricControl;
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
import com.knight.wanandroid.library_base.entity.LoginEntity;
import com.knight.wanandroid.module_mine.fragment.QuickBottomFragment;
import com.knight.wanandroid.module_mine.model.QuickLoginModel;
import com.knight.wanandroid.module_mine.presenter.QuickPresenter;
import org.greenrobot.eventbus.EventBus;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/22 17:16
 * @descript:快捷登录(包括指纹登录和手势登录)
 */
@Route(path = RoutePathActivity.Mine.QuickLogin_Pager)
public final class QuickLoginActivity extends BaseActivity<MineActivityQuickloginBinding, QuickPresenter, QuickLoginModel> implements QuickLoginContract.QuickLoginView,
        QuickBottomFragment.FingureLoginListener {

    private QuickBottomFragment mQuickBottomFragment;

    GestureLockView.OnCheckPasswordListener onCheckPasswordListener = new GestureLockView.OnCheckPasswordListener() {
        @Override
        public boolean onCheckPassword(String passwd) {
            byte[] gesturePassword = Base64.decode(CacheUtils.getGesturePassword(), Base64.URL_SAFE);
            return passwd.equals(new String(gesturePassword));
        }

        @Override
        public void onSuccess() {
            //调用登录接口
            String localLoginMessage = CacheUtils.getLoginMessage();
            LoginEntity loginEntity = GsonUtils.get(localLoginMessage, LoginEntity.class);
            mPresenter.requestUserInfo(loginEntity.getLoginName(), loginEntity.getLoginPassword());
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
        mDatabind.setClick(new ProxyClick());
        mDatabind.mineTvGestureTime.setText(DateUtils.convertTime() + "~");
        mDatabind.includeQuickLoginToolbar.baseTvTitle.setText(R.string.mine_quick_login);
        mDatabind.mineGesturelock.setDotPressedColor(themeColor);
        mDatabind.mineGesturelock.setLineColor(themeColor);
        mDatabind.includeQuickLoginToolbar.baseIvBack.setOnClickListener(v -> finish());
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
        CacheUtils.saveDataInfo(MMkvConstants.USER, userInfo);
        //登录成功发送事件
        EventBus.getDefault().post(new EventBusUtils.LoginInSuccess());
        finish();
    }



    public class ProxyClick{
        public void showBottomDialog() {
            mQuickBottomFragment = new QuickBottomFragment();
            mQuickBottomFragment.setFingureLoginListener(QuickLoginActivity.this);
            mQuickBottomFragment.showAllowingStateLoss(getSupportFragmentManager(), "QuickBottomFragment");
        }
    }

    @Override
    public void fingureQuick() {

        BiometricControl.loginBlomtric(this, new BiometricControl.BiometricStatusCallback() {
            @Override
            public void onUsePassword() {
                startActivity(
                        new Intent(QuickLoginActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onVerifySuccess(Cipher cipher) {
                String text = CacheUtils.getEncryptLoginMessage();
                byte[] input = Base64.decode(text, Base64.URL_SAFE);
                byte[] bytes;
                try {
                    bytes = cipher.doFinal(input);
                    /**
                     * 然后这里用原密码(当然是加密过的)调登录接口
                     */
                    LoginEntity loginEntity = GsonUtils.get(new String(bytes), LoginEntity.class);
                    byte[] iv = cipher.getIV();
                    mPresenter.requestUserInfo(loginEntity.getLoginName(),loginEntity.getLoginPassword());
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {
                startActivity(new Intent(QuickLoginActivity.this, LoginActivity.class));
            }

            @Override
            public void error(int code, String reason) {
                ToastUtils.show(code + "," + reason);

            }

            @Override
            public void onCancel() {
                ToastUtils.show(R.string.base_permission_denied);

            }
        });
    }
}
