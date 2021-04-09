package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.BannerModel;
import com.knight.wanandroid.module_home.module_entity.TopArticleModel;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:20
 * @descript:首页逻辑处理层
 */
public class HomePresenter extends HomeContract.HomeDataPresenter {


    @Override
    public void requestBannerData(BaseDBActivity activity) {
        final HomeContract.HomeView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestBannerData(activity, new MvpListener<List<BannerModel>>() {
            @Override
            public void onSuccess(List<BannerModel> bannerModel) {
               mView.setBannerData(bannerModel);
            }

            @Override
            public void onError(String errorMsg) {
               mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void requestTopArticle(BaseDBActivity activity) {
        final HomeContract.HomeView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestTopArticle(activity, new MvpListener<List<TopArticleModel>>() {
            @Override
            public void onSuccess(List<TopArticleModel> topArticleModels) {
                mView.setTopArticle(topArticleModels);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
