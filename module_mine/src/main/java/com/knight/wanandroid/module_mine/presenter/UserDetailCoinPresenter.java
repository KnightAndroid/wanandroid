package com.knight.wanandroid.module_mine.presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.contract.UserDetailCoinContract;
import com.knight.wanandroid.module_mine.entity.UserDetailCoinListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 11:15
 * @descript:
 */
public final class UserDetailCoinPresenter extends UserDetailCoinContract.DetailCoinDataPresenter {

    
    @Override
    public void requestUserDetailCoin(int page) {
        final UserDetailCoinContract.DetailCoinView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestUserDetailCoin((BaseActivity) mView, page, new MvpListener<UserDetailCoinListEntity>() {
            @Override
            public void onSuccess(UserDetailCoinListEntity data) {
                mView.setUserDetailCoin(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });



    }
}
