package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
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
     * 首页列表请求api
     *
     * @param activity
     * @param page
     */
    @Override
    public void requestAllHomeArticle(BaseDBActivity activity,int page) {
        final HomeArticleContract.HomeArticleView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestAllHomeArticle(activity, page,new MvpListener<HomeArticleListEntity>() {
            @Override
            public void onSuccess(HomeArticleListEntity data) {
                mView.setAllHomeArticle(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }

    /**
     *
     * 根据搜索去获取文章数据
     * @param activity
     * @param page
     * @param keyWords
     */
    @Override
    public void requestSearchArticle(BaseDBActivity activity, int page, String keyWords) {
        final HomeArticleContract.HomeArticleView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestSerchArticle(activity, page, keyWords, new MvpListener<HomeArticleListEntity>() {
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


}
