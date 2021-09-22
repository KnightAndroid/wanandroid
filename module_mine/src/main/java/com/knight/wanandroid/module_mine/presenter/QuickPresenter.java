package com.knight.wanandroid.module_mine.presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.contract.QuickLoginContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/22 19:09
 * @descript:
 */
public final class QuickPresenter extends QuickLoginContract.QuickLoginDataPresenter {
    @Override
    public void requestUserInfo(String username, String password) {
        final QuickLoginContract.QuickLoginView mView = getView();
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
