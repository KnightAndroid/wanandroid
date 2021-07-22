package com.knight.wanandroid.module_mine.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.module_contract.MineContract;
import com.knight.wanandroid.module_mine.module_entity.UserInfoCoinEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:14
 * @descript:
 */
public class MinePresenter extends MineContract.MineDataPresenter {


    @Override
    public void requestUserInfoCoin() {
        final MineContract.MineView mView = getView();
        if (mView ==null) {
            return;
        }
        mModel.requestUserInfoCoin((BaseFragment) mView, new MvpListener<UserInfoCoinEntity>() {
            @Override
            public void onSuccess(UserInfoCoinEntity userInfoCoinEntity) {
                    mView.setUserInfoCoin(userInfoCoinEntity);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });


    }

}
