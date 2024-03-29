package com.knight.wanandroid.module_mine.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_biometric.control.BiometricControl;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_util.SoftInputScrollUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.contract.RegisterContract;
import com.knight.wanandroid.module_mine.databinding.MineActivityRegisterBinding;
import com.knight.wanandroid.library_base.entity.LoginEntity;
import com.knight.wanandroid.module_mine.model.RegisterModel;
import com.knight.wanandroid.module_mine.presenter.RegisterPresenter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * @author created by kinght
 * @organize wanandroid
 * @Date 2021/4/22 15:25
 * @descript:注册页面
 */
@Route(path = RoutePathActivity.Mine.Register_Pager)
public final class RegisterActivity extends BaseActivity<MineActivityRegisterBinding, RegisterPresenter, RegisterModel> implements RegisterContract.RegisterView {
    private SoftInputScrollUtils mSoftInputScrollUtils;


    @Override
    public int layoutId() {
        return R.layout.mine_activity_register;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(CacheUtils.getThemeColor());
        gradientDrawable.setCornerRadius(ScreenUtils.dp2px(45));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDatabind.mineTvRegister.setBackground(gradientDrawable);
        } else {
            mDatabind.mineTvRegister.setBackgroundDrawable(gradientDrawable);
        }

        GradientDrawable cursorDrawable = new GradientDrawable();
        cursorDrawable.setShape(GradientDrawable.RECTANGLE);
        cursorDrawable.setColor(themeColor);
        cursorDrawable.setSize(ScreenUtils.dp2px(2),ScreenUtils.dp2px(2));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mDatabind.mineRegisterUsername.setTextCursorDrawable(cursorDrawable);
            mDatabind.mineRegisterPassword.setTextCursorDrawable(cursorDrawable);
            mDatabind.mineRegisterConfirmpassword.setTextCursorDrawable(cursorDrawable);
        } else {
            SystemUtils.setCursorDrawableColor(mDatabind.mineRegisterUsername,themeColor);
            SystemUtils.setCursorDrawableColor(mDatabind.mineRegisterPassword,themeColor);
            SystemUtils.setCursorDrawableColor(mDatabind.mineRegisterConfirmpassword,themeColor);
        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        mSoftInputScrollUtils = SoftInputScrollUtils.attach(this).moveBy(mDatabind.mineRegisterRoot);
        mSoftInputScrollUtils.moveWith(mDatabind.mineTvRegister,mDatabind.mineRegisterUsername,mDatabind.mineRegisterPassword,mDatabind.mineRegisterConfirmpassword);
        mDatabind.mineRegisterToolbar.baseTvTitle.setText(getString(R.string.mine_register));
        mDatabind.mineRegisterToolbar.baseIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
        String loginMessage = GsonUtils.toJson(new LoginEntity(mDatabind.mineRegisterUsername.getText().toString().trim(),mDatabind.mineRegisterPassword.getText().toString().trim()));
        CacheUtils.setLoginMessage(loginMessage);
        openBlomtric(loginMessage);
    }


    public class ProcyClick{
        public void register(){
            if(validateRegisterMessage()){
                mPresenter.requestRegister(
                        mDatabind.mineRegisterUsername.getText().toString().trim(),mDatabind.mineRegisterPassword.getText().toString().trim(),mDatabind.mineRegisterConfirmpassword.getText().toString().trim());
            }
        }

    }

    /**
     *
     * 验证注册信息
     * @return
     */
    private boolean validateRegisterMessage(){
        boolean validFlag = true;
        if (TextUtils.isEmpty(mDatabind.mineRegisterUsername.getText().toString().trim())) {
            mDatabind.mineRegisterUsername.setError(getString(R.string.mine_emptyusername_hint));
            validFlag = false;
        } else if (TextUtils.isEmpty(mDatabind.mineRegisterPassword.getText().toString().trim())) {
            mDatabind.mineRegisterPassword.setError(getString(R.string.mine_emptypassword_hint));
            validFlag = false;
        } else if (TextUtils.isEmpty(mDatabind.mineRegisterConfirmpassword.getText().toString().trim())) {
            mDatabind.mineRegisterConfirmpassword.setError(getString(R.string.mine_confirmpassowrd_notempty));
            validFlag = false;
        } else if (!mDatabind.mineRegisterPassword.getText().toString().trim().equals(mDatabind.mineRegisterConfirmpassword.getText().toString().trim())){
            mDatabind.mineRegisterConfirmpassword.setError(getString(R.string.mine_passworduneaual));
            validFlag = false;
        }

        return validFlag;
    }


    /**
     *
     * 开通指纹登录
     * @param loginMessage
     */
    private void openBlomtric(String loginMessage) {
        BiometricControl.openBlomtric(this, new BiometricControl.BiometricStatusCallback() {
            @Override
            public void onUsePassword() {
                finish();
            }

            @Override
            public void onVerifySuccess(Cipher cipher) {
                byte[] bytes;
                try {
                    bytes = cipher.doFinal(loginMessage.getBytes());
                    //保存加密过后的字符串
                    CacheUtils.setEncryptLoginMessage(Base64.encodeToString(bytes, Base64.URL_SAFE));
                    byte[] iv = cipher.getIV();
                    CacheUtils.setCliperIv(Base64.encodeToString(iv, Base64.URL_SAFE));
                    //保存开启了快捷登录
                    CacheUtils.setFingerLogin(true);
                    finish();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {
                finish();
            }

            @Override
            public void error(int code, String reason) {
                ToastUtils.show(code+","+reason);
                finish();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }
}
