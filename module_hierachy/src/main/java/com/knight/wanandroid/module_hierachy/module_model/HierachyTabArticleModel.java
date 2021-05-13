package com.knight.wanandroid.module_hierachy.module_model;

import com.knight.wanandroid.library_base.api.CancelCollectArticleApi;
import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyTabContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyTabArticleListEntity;
import com.knight.wanandroid.module_hierachy.module_request.HierachyTabArticelApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/12 17:24
 * @descript:
 */
public class HierachyTabArticleModel implements HierachyTabContract.HierachyTabModel {

    @Override
    public void requestHierachyTabArticles(BaseFragment fragment, int page, int cid, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new HierachyTabArticelApi().setPage(page).setCid(cid))
                .request(new HttpCallback<HttpData<HierachyTabArticleListEntity>>(fragment){

                    @Override
                    public void onSucceed(HttpData<HierachyTabArticleListEntity> result) {
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
}
