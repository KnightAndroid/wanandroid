package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.OtherArticleContract;
import com.knight.wanandroid.module_mine.module_entity.OtherShareArticleListEntity;
import com.knight.wanandroid.module_mine.module_request.OtherShareArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 18:55
 * @descript:
 */
public class OtherShareArticleModel implements OtherArticleContract.OtherShareArticleModel {
    @Override
    public void requestOtherShareArticle(BaseActivity activity, int uid, int page, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new OtherShareArticleApi().setUid(uid).setPage(page))
                .request(new HttpCallback<HttpData<OtherShareArticleListEntity>>(activity){

                    @Override
                    public void onSucceed(HttpData<OtherShareArticleListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });
    }
}
