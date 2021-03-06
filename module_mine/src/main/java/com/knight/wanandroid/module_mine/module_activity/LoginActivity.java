package com.knight.wanandroid.module_mine.module_activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.SoftInputScrollUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.constant.MMkvConstants;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityLoginBinding;
import com.knight.wanandroid.module_mine.module_contract.LoginContract;
import com.knight.wanandroid.module_mine.module_model.LoginModel;
import com.knight.wanandroid.module_mine.module_presenter.LoginPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/19 17:49
 * @descript登录界面
 */

@Route(path = RoutePathActivity.Mine.Login_Pager)
public class LoginActivity extends BaseActivity<MineActivityLoginBinding, LoginPresenter, LoginModel> implements LoginContract.LoginView {

    private SoftInputScrollUtils mSoftInputScrollUtils;


    @Override
    public int layoutId() {
        return R.layout.mine_activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        mSoftInputScrollUtils = SoftInputScrollUtils.attach(this).moveBy(mDatabind.mineLoginRoot);
        mSoftInputScrollUtils.moveWith(mDatabind.mineTvLogin,mDatabind.mineLoginUsername,mDatabind.mineLoginPassword);
        mDatabind.mineLoginToolbar.baseTvTitle.setText("登录");
        mDatabind.mineLoginToolbar.baseIvBack.setOnClickListener(new View.OnClickListener() {
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
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Override
    public void setUserInfo(UserInfoEntity userInfo) {
        //保存用户信息
        CacheUtils.getInstance().saveDataInfo(MMkvConstants.USER,userInfo);
        //登录成功发送事件
        EventBus.getDefault().post(new EventBusUtils.LoginInSuccess());
        finish();
    }




    public class ProcyClick{
        public void loginIn(){
            if (validateLoginMessage()) {
                mPresenter.requestUserInfo(mDatabind.mineLoginUsername.getText().toString().trim(),mDatabind.mineLoginPassword.getText().toString().trim());
            }
        }

        public void goRegister(){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
    }


    private boolean validateLoginMessage(){
        boolean validFlag = true;
        if (TextUtils.isEmpty(mDatabind.mineLoginUsername.getText().toString().trim())) {
            mDatabind.mineLoginUsername.setError(getString(R.string.mine_emptyusername_hint));
            validFlag = false;
        } else if (TextUtils.isEmpty(mDatabind.mineLoginPassword.getText().toString().trim())) {
            mDatabind.mineLoginPassword.setError(getString(R.string.mine_emptypassword_hint));
            validFlag = false;
        }

        return validFlag;
    }
}
