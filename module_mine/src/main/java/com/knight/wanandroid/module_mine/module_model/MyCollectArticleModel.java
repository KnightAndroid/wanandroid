package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.MyCollectArticleContract;
import com.knight.wanandroid.module_mine.module_entity.MyCollectArticleListEntity;
import com.knight.wanandroid.module_mine.module_request.MyCollectArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 17:22
 * @descript:
 */
public class MyCollectArticleModel implements MyCollectArticleContract.MyCollectArticleModel {
    @Override
    public void requestCollectArticles(BaseActivity activity, int page, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new MyCollectArticleApi().setPage(page))
                .request(new HttpCallback<HttpData<MyCollectArticleListEntity>>(activity){

                    @Override
                    public void onSucceed(HttpData<MyCollectArticleListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });
    }
}
