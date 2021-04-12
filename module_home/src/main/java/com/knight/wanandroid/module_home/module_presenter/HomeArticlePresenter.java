package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_home.module_contract.HomeArticleContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListModel;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:26
 * @descript:文章逻辑业务层
 */
public class HomeArticlePresenter extends HomeArticleContract.HomeArticleDataPresenter {
    @Override
    public void requestHomeArticle(BaseDBActivity activity) {
        final HomeArticleContract.HomeArticleView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestHomeArticle(activity, new MvpListener<HomeArticleListModel>() {
            @Override
            public void onSuccess(HomeArticleListModel data) {
                mView.setHomeArticle(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
