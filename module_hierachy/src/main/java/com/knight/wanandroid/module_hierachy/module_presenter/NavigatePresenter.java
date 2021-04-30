package com.knight.wanandroid.module_hierachy.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
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
public class NavigatePresenter extends NavigateContract.NavigateDataPrensenter {

    @Override
    public void requestNavigateData(BaseDBActivity activity) {
        final NavigateContract.NavigateView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestNavigateData(activity, new MvpListener<List<NavigateListEntity>>() {
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
