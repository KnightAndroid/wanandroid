package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.HomeArticleContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:26
 * @descript:文章逻辑业务层
 */
public class HomeArticlePresenter extends HomeArticleContract.HomeArticleDataPresenter {




    /**
     *
     * 根据搜索去获取文章数据
     *
     * @param page
     * @param keyWords
     */
    @Override
    public void requestSearchArticle(int page, String keyWords) {
        final HomeArticleContract.HomeArticleView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestSerchArticle((BaseFragment)mView, page, keyWords, new MvpListener<HomeArticleListEntity>() {
            @Override
            public void onSuccess(HomeArticleListEntity data) {
                mView.setSearchArticle(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void requestCollectArticle(int collectArticleId,int position) {
        final HomeArticleContract.HomeArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCollectArticle((BaseFragment)mView,collectArticleId,new MvpListener<HttpData>(){
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
        final HomeArticleContract.HomeArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCancelCollectArticle((BaseFragment)mView,collectArticleId,new MvpListener<HttpData>(){
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
