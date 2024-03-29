package com.knight.wanandroid.module_mine.presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.contract.RegisterContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/22 16:23
 * @descript:
 */
public final class RegisterPresenter extends RegisterContract.RegisterDataPresenter {



    @Override
    public void requestRegister(String username,String password,String repassword) {
        final RegisterContract.RegisterView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestRegister((BaseActivity) mView, username,password,repassword,new MvpListener<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity data) {
                mView.setUserInfo(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
