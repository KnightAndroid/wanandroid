package com.knight.wanandroid.module_hierachy.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_hierachy.module_contract.NavigateContract;
import com.knight.wanandroid.module_hierachy.module_entity.NavigateListEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/30 14:48
 * @descript:导航
 */
public final class NavigatePresenter extends NavigateContract.NavigateDataPrensenter {

    @Override
    public void requestNavigateData() {
        final NavigateContract.NavigateView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestNavigateData((BaseFragment)mView, new MvpListener<List<NavigateListEntity>>() {
            @Override
            public void onSuccess(List<NavigateListEntity> data) {
                mView.setNavigateData(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }
}
