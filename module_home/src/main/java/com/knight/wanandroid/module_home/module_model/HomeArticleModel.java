package com.knight.wanandroid.module_home.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.HomeArticleContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.module_home.module_request.HomeArticleApi;
import com.knight.wanandroid.module_home.module_request.SearchArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:54
 * @descript:
 */
public class HomeArticleModel implements HomeArticleContract.HomeArticleModel{

    /**
     *
     * 请求文章数据
     * @param activity
     * @param page
     * @param mvpListener
     */
    @Override
    public void requestAllHomeArticle(BaseDBActivity activity, int page,MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new HomeArticleApi()
                        .setPage(page))
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


    /**
     *
     * 搜索文章请求数据
     * @param activity
     * @param page
     * @param keyWords
     * @param mvpListener
     */
    @Override
    public void requestSerchArticle(BaseDBActivity activity, int page, String keyWords, MvpListener mvpListener) {
        GoHttp.post(activity)
                .api(new SearchArticleApi().setPage(page).setKeyWord(keyWords))
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
}
