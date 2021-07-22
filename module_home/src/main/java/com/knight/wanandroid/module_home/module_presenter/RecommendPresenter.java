package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_home.module_contract.RecommendContract;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/21 15:31
 * @descript:
 */
public class RecommendPresenter extends RecommendContract.RecommendDataPresenter {
    @Override
    public void requestBannerData() {
        final RecommendContract.RecommendView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestBannerData((BaseFragment)mView, new MvpListener<List<BannerEntity>>() {
            @Override
            public void onSuccess(List<BannerEntity> bannerModel) {
                mView.setBannerData(bannerModel);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    @Override
    public void requestTopArticle() {
        final RecommendContract.RecommendView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestTopArticle((BaseFragment)mView, new MvpListener<List<TopArticleEntity>>() {
            @Override
            public void onSuccess(List<TopArticleEntity> topArticleEntities) {
                mView.setTopArticle(topArticleEntities);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    @Override
    public void requestOfficialAccountData() {
        final RecommendContract.RecommendView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestOfficialAccountData((BaseFragment)mView, new MvpListener<List<OfficialAccountEntity>>() {

            @Override
            public void onSuccess(List<OfficialAccountEntity> data) {
                mView.setOfficialAccountData(data);

            }

            @Override
            public void onError(String errorMsg) {


            }
        });
    }

    /**
     *
     * 首页列表请求api
     *
     *
     * @param page
     */
    @Override
    public void requestAllHomeArticle(int page) {
        final RecommendContract.RecommendView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestAllHomeArticle((BaseFragment)mView, page,new MvpListener<HomeArticleListEntity>() {
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

    @Override
    public void requestUnreadMessage() {
        final RecommendContract.RecommendView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestUnreadMessage((BaseFragment) mView, new MvpListener<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                mView.setUnreadMessage(data);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });

    }
}
