package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;
import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;

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
     *
     */
    @Override
    public void requestBannerData() {
        final HomeContract.HomeView mView = getView();
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
               mView.showError(errorMsg);
            }
        });
    }

    /**
     *
     * 置顶文章获取
     *
     */
    @Override
    public void requestTopArticle() {
        final HomeContract.HomeView mView = getView();
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
                mView.showError(errorMsg);
            }
        });
    }



    /**
     *
     * 公众号列表数据获取
     */
    @Override
    public void requestOfficialAccountData() {
        final HomeContract.HomeView mView = getView();
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
                mView.showError(errorMsg);

            }
        });
    }

    @Override
    public void requestAppUpdateMessage() {
        final HomeContract.HomeView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestAppUpdateMessage((BaseFragment)mView, new MvpListener<AppUpdateEntity>() {
            @Override
            public void onSuccess(AppUpdateEntity appUpdateEntity) {
                mView.setAppUpdateMessage(appUpdateEntity);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }

    @Override
    public void requestEveryDayPushArticle() {
        final HomeContract.HomeView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestEveryDayPushArticle((BaseFragment) mView, new MvpListener<EveryDayPushEntity>() {
            @Override
            public void onSuccess(EveryDayPushEntity everyDayPushEntity) {
                mView.setEveryDayPushArticle(everyDayPushEntity);

            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

}
