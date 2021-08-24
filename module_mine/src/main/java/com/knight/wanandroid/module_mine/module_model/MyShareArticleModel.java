package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.MyShareArticleContract;
import com.knight.wanandroid.module_mine.module_entity.MyShareEntity;
import com.knight.wanandroid.module_mine.module_request.DeleteShareArticleApi;
import com.knight.wanandroid.module_mine.module_request.MyShareArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/8 16:47
 * @descript:
 */
public final class MyShareArticleModel implements MyShareArticleContract.MyShareArticleModel {

    @Override
    public void requestMyShareArticle(BaseActivity activity, int page,MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new MyShareArticleApi().setPage(page))
                .request(new HttpCallback<HttpData<MyShareEntity>>(activity){
                    
                    @Override
                    public void onSucceed(HttpData<MyShareEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void requestDeleteCollectArticle(BaseActivity activity, int articleId, MvpListener mvpListener) {
        GoHttp.post(activity)
                .api(new DeleteShareArticleApi().setArticleId(articleId))
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
