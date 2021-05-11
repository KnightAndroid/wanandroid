package com.knight.wanandroid.module_mine.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.module_contract.MyCollectArticleContract;
import com.knight.wanandroid.module_mine.module_entity.MyCollectArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 17:30
 * @descript:
 */
public class MyCollectArticlePresenter extends MyCollectArticleContract.MyCollectDataPresenter {
    @Override
    public void requestCollectArticles(int page) {
        final MyCollectArticleContract.MyCollectArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCollectArticles((BaseActivity) mView, page, new MvpListener<MyCollectArticleListEntity>() {
            @Override
            public void onSuccess(MyCollectArticleListEntity data) {
                mView.setCollectArticles(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }
}
