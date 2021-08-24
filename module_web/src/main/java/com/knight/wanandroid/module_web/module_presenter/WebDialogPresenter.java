package com.knight.wanandroid.module_web.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseDialogFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_web.module_contract.WebDialogContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 11:27
 * @descript:
 */
public final class WebDialogPresenter extends WebDialogContract.WebDialogDataPresenter {
    @Override
    public void requestCollectArticle(int collectArticleId) {
        final WebDialogContract.WebDialogView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCollectArticle((BaseDialogFragment) mView,collectArticleId,new MvpListener<HttpData>(){
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
