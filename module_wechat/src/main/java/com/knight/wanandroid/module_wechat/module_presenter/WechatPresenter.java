package com.knight.wanandroid.module_wechat.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_wechat.module_contract.WechatArticleContract;
import com.knight.wanandroid.module_wechat.module_entity.WechatArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/14 15:06
 * @descript:
 */
public class WechatPresenter extends WechatArticleContract.WechatArticleDataPresenter {
    @Override
    public void requestWechatArticle(int page, int cid) {
        final WechatArticleContract.WechatArticleView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestWechatArticle((BaseFragment) mView, page, cid, new MvpListener<WechatArticleListEntity>() {
            @Override
            public void onSuccess(WechatArticleListEntity data) {
                mView.setWechatArticle(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });


    }

    @Override
    public void requestCollectArticle(int collectArticleId, int position) {
        final WechatArticleContract.WechatArticleView mView = getView();
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
        final WechatArticleContract.WechatArticleView mView = getView();
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

    @Override
    public void requestArticlesByKeywords(int page, int cid, String keywords) {
        final WechatArticleContract.WechatArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestArticlesByKeywords((BaseFragment)mView,page,cid,keywords,new MvpListener<WechatArticleListEntity>(){
            @Override
            public void onSuccess(WechatArticleListEntity data) {
                mView.setSearchArticlesByKeyword(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
