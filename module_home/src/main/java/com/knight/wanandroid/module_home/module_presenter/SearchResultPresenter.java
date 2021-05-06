package com.knight.wanandroid.module_home.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_home.module_contract.SearchResultContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 18:42
 * @descript:
 */
public class SearchResultPresenter extends SearchResultContract.SearchResultDataPresenter {
    @Override
    public void requestSearchResult(BaseActivity activity, int page, String keyWords) {
        final SearchResultContract.SearchResultView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestSerchArticle(activity, page, keyWords, new MvpListener<HomeArticleListEntity>() {
            @Override
            public void onSuccess(HomeArticleListEntity data) {
                mView.setSearchResultData(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
