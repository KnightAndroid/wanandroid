package com.knight.wanandroid.module_square.module_presenter;

import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_square.module_contract.SquareContact;
import com.knight.wanandroid.module_square.module_entity.SquareArticleListEntity;
import com.knight.wanandroid.module_square.module_entity.SquareQuestionListEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:30
 * @descript:
 */
public class SquarePresenter extends SquareContact.SquareDataPresenter{


    @Override
    public void requestHotKey() {
        final SquareContact.SquareView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestHotKey((BaseFragment)mView, new MvpListener<List<SearchHotKeyEntity>>() {
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
    public void requestShareData( int page) {
        final SquareContact.SquareView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestShareArticles((BaseFragment)mView, page,new MvpListener<SquareArticleListEntity>() {
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

    @Override
    public void requestCollectArticle(int collectArticleId, int position) {
        final SquareContact.SquareView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCollectArticle((BaseFragment) mView,collectArticleId,new MvpListener<HttpData>(){
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
        final SquareContact.SquareView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCancelCollectArticle((BaseFragment) mView,collectArticleId,new MvpListener<HttpData>(){
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
    public void requestSquareQuestion(int page) {
        final SquareContact.SquareView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestQuestions((BaseFragment) mView, page, new MvpListener<SquareQuestionListEntity>() {
            @Override
            public void onSuccess(SquareQuestionListEntity data) {
                mView.setSquareQuestionData(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }

}
