package com.knight.wanandroid.module_hierachy.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyListEntity;

import java.util.ArrayList;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 15:37
 * @descript:体系
 */
public class HierachyPresenter extends HierachyContract.HierachyDataPresenter {


    @Override
    public void requestHierachyData(BaseDBActivity activity) {
        final HierachyContract.HierachyView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestHierachyData(activity, new MvpListener<ArrayList<HierachyListEntity>>() {
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
}
