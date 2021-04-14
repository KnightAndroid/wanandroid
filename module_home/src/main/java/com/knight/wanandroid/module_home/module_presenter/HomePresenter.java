package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.OfficialAccountEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:20
 * @descript:首页逻辑处理层
 */
public class HomePresenter extends HomeContract.HomeDataPresenter {


    /**
     *
     * banner图获取
     * @param activity
     */
    @Override
    public void requestBannerData(BaseDBActivity activity) {
        final HomeContract.HomeView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestBannerData(activity, new MvpListener<List<BannerEntity>>() {
            @Override
            public void onSuccess(List<BannerEntity> bannerModel) {
               mView.setBannerData(bannerModel);
            }

            @Override
            public void onError(String errorMsg) {
               mView.showError(errorMsg);
            }
        });
    }

    /**
     *
     * 置顶文章获取
     * @param activity
     */
    @Override
    public void requestTopArticle(BaseDBActivity activity) {
        final HomeContract.HomeView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestTopArticle(activity, new MvpListener<List<TopArticleEntity>>() {
            @Override
            public void onSuccess(List<TopArticleEntity> topArticleEntities) {
                mView.setTopArticle(topArticleEntities);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }



    /**
     *
     * 公众号列表数据获取
     */
    @Override
    public void requestOfficialAccountData(BaseDBActivity activity) {
        final HomeContract.HomeView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestOfficialAccountData(activity, new MvpListener<List<OfficialAccountEntity>>() {

            @Override
            public void onSuccess(List<OfficialAccountEntity> data) {
                mView.setOfficialAccountData(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);

            }
        });
    }

}
