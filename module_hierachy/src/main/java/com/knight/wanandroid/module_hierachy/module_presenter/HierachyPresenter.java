package com.knight.wanandroid.module_hierachy.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyListEntity;
import com.knight.wanandroid.module_hierachy.module_entity.NavigateListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 15:37
 * @descript:体系
 */
public class HierachyPresenter extends HierachyContract.HierachyDataPresenter {


    /**
     *
     * 体系请求
     *
     */
    @Override
    public void requestHierachyData() {
        final HierachyContract.HierachyView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestHierachyData((BaseFragment)mView, new MvpListener<ArrayList<HierachyListEntity>>() {
            @Override
            public void onSuccess(ArrayList<HierachyListEntity> data) {
                mView.setHierachyData(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);

            }
        });

    }

    @Override
    public void requestNavigateData() {
        final HierachyContract.HierachyView mView = getView();
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
