package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_home.module_contract.SearchContract;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 15:52
 * @descript:搜索p层
 */
public class SearchPresenter extends SearchContract.SearchDataPresenter {
    @Override
    public void requestSearchHotkey() {
        final SearchContract.SearchView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestSearchHotkey((BaseActivity)mView,new MvpListener<List< SearchHotKeyEntity >>() {
            @Override
            public void onSuccess(List<SearchHotKeyEntity> data) {
                mView.setSearchHotKey(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }
}
