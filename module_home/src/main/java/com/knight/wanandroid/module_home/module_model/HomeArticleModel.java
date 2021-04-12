package com.knight.wanandroid.module_home.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.HomeArticleContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListModel;
import com.knight.wanandroid.module_home.module_request.HomeArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:54
 * @descript:
 */
public class HomeArticleModel implements HomeArticleContract.HomeArticleModel{
    @Override
    public void requestHomeArticle(BaseDBActivity activity, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new HomeArticleApi())
                .request(new HttpCallback<HttpData<HomeArticleListModel>>(activity){
                    @Override
                    public void onSucceed(HttpData<HomeArticleListModel> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });
    }
}
