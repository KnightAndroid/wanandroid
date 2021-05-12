package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.SearchResultContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 18:42
 * @descript:
 */
public class SearchResultPresenter extends SearchResultContract.SearchResultDataPresenter {
    @Override
    public void requestSearchResult(int page, String keyWords) {
        final SearchResultContract.SearchResultView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestSerchArticle((BaseActivity)mView, page, keyWords, new MvpListener<HomeArticleListEntity>() {
            @Override
            public void onSuccess(HomeArticleListEntity data) {
                mView.setSearchResultData(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }


    @Override
    public void requestCollectArticle(int collectArticleId,int position) {
        final SearchResultContract.SearchResultView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCollectArticle((BaseActivity) mView,collectArticleId,new MvpListener<HttpData>(){
            @Override
            public void onSuccess(HttpData data) {
                mView.collectArticleSuccess(position);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }

    @Override
    public void requestCancelCollectArticle(int collectArticleId, int position) {
        final SearchResultContract.SearchResultView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCancelCollectArticle((BaseActivity) mView,collectArticleId,new MvpListener<HttpData>(){
            @Override
            public void onSuccess(HttpData data) {
                mView.cancelArticleSuccess(position);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
