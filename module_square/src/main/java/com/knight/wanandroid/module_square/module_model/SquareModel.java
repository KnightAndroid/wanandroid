package com.knight.wanandroid.module_square.module_model;

import com.knight.wanandroid.library_base.api.CancelCollectArticleApi;
import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.library_base.api.HotKeyApi;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_square.module_contract.SquareContact;
import com.knight.wanandroid.module_square.module_entity.SquareArticleListEntity;
import com.knight.wanandroid.module_square.module_entity.SquareQuestionListEntity;
import com.knight.wanandroid.module_square.module_request.SquareArticleApi;
import com.knight.wanandroid.module_square.module_request.SquareQuestionApi;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:23
 * @descript:
 */
public final class SquareModel implements SquareContact.SquareModel {


    /**
     * 搜索热词
     *
     * @param fragment
     * @param mvpListener
     */
    @Override
    public void requestHotKey(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new HotKeyApi())
                .request(new HttpCallback<HttpData<List<SearchHotKeyEntity>>>(fragment) {
                    @Override
                    public void onSucceed(HttpData<List<SearchHotKeyEntity>> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mvpListener.onError(e.getMessage());
                    }
                });

    }

    @Override
    public void requestShareArticles(BaseFragment fragment, int page, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new SquareArticleApi().setPage(page))
                .request(new HttpCallback<HttpData<SquareArticleListEntity>>(fragment) {
                             @Override
                             public void onSucceed(HttpData<SquareArticleListEntity> result) {
                                 mvpListener.onSuccess(result.getData());
                             }

                             @Override
                             public void onFail(Exception e) {
                                 mvpListener.onError(e.getMessage());
                             }
                         }

                );
    }

    @Override
    public void requestCollectArticle(BaseFragment fragment, int collectArticleId, MvpListener mvpListener) {
        GoHttp.post(fragment)
                .api(new CollectArticleApi().setCollectArticleId(collectArticleId))
                .request(new HttpCallback<HttpData>(fragment){
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
    public void requestCancelCollectArticle(BaseFragment fragment, int collectArticleId, MvpListener mvpListener) {
        GoHttp.post(fragment)
                .api(new CancelCollectArticleApi().setCancelArticleId(collectArticleId))
                .request(new HttpCallback<HttpData>(fragment){
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
    public void requestQuestions(BaseFragment fragment, int page, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new SquareQuestionApi().setPage(page))
                .request(new HttpCallback<HttpData<SquareQuestionListEntity>>(fragment) {
                    @Override
                    public void onSucceed(HttpData<SquareQuestionListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mvpListener.onError(e.getMessage());
                    }
                });
    }


}
