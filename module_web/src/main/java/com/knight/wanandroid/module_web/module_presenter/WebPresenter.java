package com.knight.wanandroid.module_web.module_presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_web.module_contract.WebContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 16:08
 * @descript:
 */
public class WebPresenter extends WebContract.WebDataPresenter {
    @Override
    public void requestCollectArticle(int collectArticleId) {
        final WebContract.WebView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCollectArticle((BaseActivity) mView,collectArticleId,new MvpListener<HttpData>(){
            @Override
            public void onSuccess(HttpData data) {
                mView.collectArticleSuccess();
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
