package com.knight.wanandroid.module_mine.module_presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.MyShareArticleContract;
import com.knight.wanandroid.module_mine.module_entity.MyShareEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/8 15:05
 * @descript:
 */
public final class MyShareArticlePresenter extends MyShareArticleContract.MyShareArticleDataPresenter {


    @Override
    public void requestMyShareArticle(int page) {
        final MyShareArticleContract.MyShareArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestMyShareArticle((BaseActivity) mView, page,new MvpListener<MyShareEntity>() {
            @Override
            public void onSuccess(MyShareEntity data) {
                mView.setMyShareArticle(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void requestDeleteCollectArticle(int articleId, int position) {
        final MyShareArticleContract.MyShareArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestDeleteCollectArticle((BaseActivity) mView, articleId,new MvpListener<HttpData>() {
            @Override
            public void onSuccess(HttpData data) {
                mView.deleteArticleSuccess(position);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
