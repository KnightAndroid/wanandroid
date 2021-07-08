package com.knight.wanandroid.module_mine.module_presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.module_contract.LoginContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/19 18:27
 * @descript:
 */
public class LoginPresenter extends LoginContract.LoginDataPresenter {
    @Override
    public void requestUserInfo(String username, String password) {
        final LoginContract.LoginView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestUserInfo((BaseActivity) mView, username, password, new MvpListener<UserInfoEntity>() {
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
