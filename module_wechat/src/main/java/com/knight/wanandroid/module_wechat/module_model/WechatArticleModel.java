package com.knight.wanandroid.module_wechat.module_model;

import com.knight.wanandroid.library_base.api.CancelCollectArticleApi;
import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_wechat.module_contract.WechatArticleContract;
import com.knight.wanandroid.module_wechat.module_entity.WechatArticleListEntity;
import com.knight.wanandroid.module_wechat.module_request.SearchWechatArticlesApi;
import com.knight.wanandroid.module_wechat.module_request.WechatArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/14 14:50
 * @descript:
 */
public class WechatArticleModel implements WechatArticleContract.WechatArticleModel {
    @Override
    public void requestWechatArticle(BaseFragment fragment, int page, int cid, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new WechatArticleApi().setCid(cid).setPage(page))
                .request(new HttpCallback<HttpData<WechatArticleListEntity>>(fragment){


                    @Override
                    public void onSucceed(HttpData<WechatArticleListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
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
    public void requestArticlesByKeywords(BaseFragment fragment, int page, int cid,String keywords, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new SearchWechatArticlesApi().setPage(page).setCid(cid).setWearchKeyword(keywords))
                .request(new HttpCallback<HttpData<WechatArticleListEntity>>(fragment){

                    @Override
                    public void onSucceed(HttpData<WechatArticleListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
    }
}
