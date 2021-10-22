package com.knight.wanandroid.module_feedback.presenter;

import com.knight.wanandroid.library_base.basefragment.BaseDialogFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_feedback.contract.FeedBackContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/21 16:33
 * @descript:
 */
public final class FeedBackPresenter extends FeedBackContract.FeedBackDataPresenter {
    @Override
    public void requestfeedBackError(int articleId) {
        final FeedBackContract.FeedBackView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestfeedBackError((BaseDialogFragment) mView, articleId, new MvpListener<HttpData>() {
            @Override
            public void onSuccess(HttpData data) {
                mView.feedBackErrorSuccess();
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
