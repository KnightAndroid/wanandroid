package com.knight.wanandroid.module_mine.module_activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.SoftInputScrollUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityRegisterBinding;
import com.knight.wanandroid.module_mine.module_contract.RegisterContract;
import com.knight.wanandroid.module_mine.module_model.RegisterModel;
import com.knight.wanandroid.module_mine.module_presenter.RegisterPresenter;

/**
 * @author created by kinght
 * @organize wanandroid
 * @Date 2021/4/22 15:25
 * @descript:注册页面
 */
@Route(path = RoutePathActivity.Mine.Register_Pager)
public class RegisterActivity extends BaseActivity<MineActivityRegisterBinding, RegisterPresenter, RegisterModel> implements RegisterContract.RegisterView {
    private SoftInputScrollUtils mSoftInputScrollUtils;


    @Override
    public int layoutId() {
        return R.layout.mine_activity_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        mSoftInputScrollUtils = SoftInputScrollUtils.attach(this).moveBy(mDatabind.mineRegisterRoot);
        mSoftInputScrollUtils.moveWith(mDatabind.mineTvRegister,mDatabind.mineRegisterUsername,mDatabind.mineRegisterPassword,mDatabind.mineRegisterConfirmpassword);
        mDatabind.mineRegisterToolbar.baseTvTitle.setText("注册");
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
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Override
    public void setUserInfo(UserInfoEntity userInfo) {
        //暂时关闭页面 用户手动进入登录页面
        finish();
    }


    public class ProcyClick{
        public void register(){
            if(validateRegisterMessage()){
                mPresenter.requestRegister(
                        mDatabind.mineRegisterUsername.getText().toString().trim(),mDatabind.mineRegisterPassword.getText().toString().trim(),mDatabind.mineRegisterConfirmpassword.getText().toString().trim());
            }
        }

    }

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
}
