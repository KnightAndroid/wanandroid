package com.knight.wanandroid.module_square.module_presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_square.module_contract.SquareShareArticleContact;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/7 16:21
 * @descript:
 */
public class SquareShareArticlePresenter extends SquareShareArticleContact.SquareSharedataPresenter {
    @Override
    public void requestShareArticle(String title,String link) {
        final SquareShareArticleContact.SquareShareArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestShareArticle((BaseActivity) mView, title, link, new MvpListener() {
            @Override
            public void onSuccess(Object data) {
                mView.successShareArticle();
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
