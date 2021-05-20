package com.knight.wanandroid.module_mine.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.module_contract.OtherArticleContract;
import com.knight.wanandroid.module_mine.module_entity.OtherShareArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 19:00
 * @descript:
 */
public class OtherShareArticlePresenter extends OtherArticleContract.OtherShareArticleDataPresenter {
    @Override
    public void requestOtherShareArticle(int uid, int page) {
        final OtherArticleContract.OtherShareArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestOtherShareArticle((BaseActivity) mView, uid,page,new MvpListener<OtherShareArticleListEntity>() {
            @Override
            public void onSuccess(OtherShareArticleListEntity data) {
                mView.setOtherShareArticle(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
