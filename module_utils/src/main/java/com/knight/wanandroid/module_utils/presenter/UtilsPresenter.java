package com.knight.wanandroid.module_utils.presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.basefragment.BaseDialogFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_utils.contract.UtilsContract;
import com.knight.wanandroid.module_utils.entity.UtilsEntity;

import java.util.List;

import okhttp3.internal.Util;

/**
 * Author:Knight
 * Time:2022/5/12 14:50
 * Description:UtilsPresenter
 */
public final class UtilsPresenter extends UtilsContract.UtilsPresenter {
    @Override
    public void requestUtilLists() {
        final UtilsContract.UtilsView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestUtilLists((BaseActivity) mView,new MvpListener<List<UtilsEntity>>(){
            @Override
            public void onSuccess(List<UtilsEntity> data) {
                mView.setUtilsData(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }
}
