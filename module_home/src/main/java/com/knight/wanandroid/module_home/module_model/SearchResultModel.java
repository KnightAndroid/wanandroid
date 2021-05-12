package com.knight.wanandroid.module_home.module_model;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.SearchResultContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.library_base.api.CancelCollectArticleApi;
import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.module_home.module_request.SearchKeywordsApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 18:13
 * @descript:
 */
public class SearchResultModel implements SearchResultContract.SearchResultModel {
    @Override
    public void requestSerchArticle(BaseActivity activity, int page, String keyWords, MvpListener mvpListener) {
        GoHttp.post(activity)
                .api(new SearchKeywordsApi().setPage(page).setKeyWord(keyWords))
                .request(new HttpCallback<HttpData<HomeArticleListEntity>>(activity){
                    @Override
                    public void onSucceed(HttpData<HomeArticleListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }

    @Override
    public void requestCollectArticle(BaseActivity activity, int collectArticleId, MvpListener mvpListener) {
        GoHttp.post(activity)
                .api(new CollectArticleApi().setCollectArticleId(collectArticleId))
                .request(new HttpCallback<HttpData>(activity){
                    @Override
                    public void onSucceed(HttpData result) {
                        mvpListener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }

    @Override
    public void requestCancelCollectArticle(BaseActivity activity, int collectArticleId, MvpListener mvpListener) {
        GoHttp.post(activity)
                .api(new CancelCollectArticleApi().setCancelArticleId(collectArticleId))
                .request(new HttpCallback<HttpData>(activity){
                    @Override
                    public void onSucceed(HttpData result) {
                        mvpListener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }
}
