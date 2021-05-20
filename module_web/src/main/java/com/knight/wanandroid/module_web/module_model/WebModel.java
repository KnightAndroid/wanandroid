package com.knight.wanandroid.module_web.module_model;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_web.module_contract.WebContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 16:05
 * @descript:
 */
public class WebModel implements WebContract.WebModel {
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
}
