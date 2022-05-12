package com.knight.wanandroid.module_mine.presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.contract.MineContract;
import com.knight.wanandroid.module_mine.entity.UserInfoCoinEntity;
import com.knight.wanandroid.module_mine.entity.UserInfoMessageEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:14
 * @descript:
 */
public final class MinePresenter extends MineContract.MineDataPresenter {


    @Override
    public void requestUserInfoCoin() {
        final MineContract.MineView mView = getView();
        if (mView ==null) {
            return;
        }
        mModel.requestUserInfoCoin((BaseFragment) mView, new MvpListener<UserInfoMessageEntity>() {
            @Override
            public void onSuccess(UserInfoMessageEntity userInfoMessageEntity) {
                    mView.setUserInfoCoin(userInfoMessageEntity);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });


    }

    @Override
    public void requestUserInfo(String username, String password) {
        final MineContract.MineView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestUserInfo((BaseFragment) mView, username, password, new MvpListener<UserInfoEntity>() {
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
