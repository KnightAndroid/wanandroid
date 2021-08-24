package com.knight.wanandroid.module_hierachy.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyTabContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyTabArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/12 17:31
 * @descript:
 */
public final class HierachyTabArticlePresenter extends HierachyTabContract.HierachyTabDataPresenter {
    @Override
    public void requestHierachyTabArticles(int page,int cid) {
        final HierachyTabContract.HierachyTabView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestHierachyTabArticles((BaseFragment) mView, page, cid, new MvpListener<HierachyTabArticleListEntity>() {
            @Override
            public void onSuccess(HierachyTabArticleListEntity data) {
                mView.setHierachyTabArticles(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }

    @Override
    public void requestCollectArticle(int collectArticleId, int position) {
        final HierachyTabContract.HierachyTabView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCollectArticle((BaseFragment) mView,collectArticleId,new MvpListener<HttpData>(){
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
        final HierachyTabContract.HierachyTabView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCancelCollectArticle((BaseFragment) mView,collectArticleId,new MvpListener<HttpData>(){
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
