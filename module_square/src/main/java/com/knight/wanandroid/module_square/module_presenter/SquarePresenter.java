package com.knight.wanandroid.module_square.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_square.module_contact.SquareContact;
import com.knight.wanandroid.module_square.module_entity.SearchHotKeyEntity;
import com.knight.wanandroid.module_square.module_entity.SquareArticleListEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:30
 * @descript:
 */
public class SquarePresenter extends SquareContact.SquareDataPresenter{


    @Override
    public void requestHotKey(BaseDBActivity activity) {
        final SquareContact.SquareView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestHotKey(activity, new MvpListener<List<SearchHotKeyEntity>>() {
            @Override
            public void onSuccess(List<SearchHotKeyEntity> data) {
                mView.setHotKey(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });


    }

    @Override
    public void requestShareData(BaseDBActivity activity, int page) {
        final SquareContact.SquareView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestShareArticles(activity, page,new MvpListener<SquareArticleListEntity>() {
            @Override
            public void onSuccess(SquareArticleListEntity data) {
                mView.setShareArticles(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }

}
