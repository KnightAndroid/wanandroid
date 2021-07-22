package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.EveryDayPushArticlesEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:20
 * @descript:首页逻辑处理层
 */
public class HomePresenter extends HomeContract.HomeDataPresenter {
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
               // mView.showError(errorMsg);
            }
        });

    }

    @Override
    public void requestEveryDayPushArticle() {
        final HomeContract.HomeView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestEveryDayPushArticle((BaseFragment) mView, new MvpListener<EveryDayPushArticlesEntity>() {
            @Override
            public void onSuccess(EveryDayPushArticlesEntity everyDayPushArticlesEntity) {
                mView.setEveryDayPushArticle(everyDayPushArticlesEntity);

            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }



}
