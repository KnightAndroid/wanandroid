package com.knight.wanandroid.module_home.module_model;

import com.knight.wanandroid.library_base.api.CancelCollectArticleApi;
import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.RecommendContract;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;
import com.knight.wanandroid.module_home.module_request.HomeArticleApi;
import com.knight.wanandroid.module_home.module_request.HomeBannerApi;
import com.knight.wanandroid.module_home.module_request.HomeOfficialAccountApi;
import com.knight.wanandroid.module_home.module_request.TopArticleApi;
import com.knight.wanandroid.module_home.module_request.UnreadMessageApi;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/21 15:27
 * @descript:
 */
public class RecommendModel implements RecommendContract.RecommendModel {

    /**
     *
     * 请求置顶数据
     * @param fragment
     * @param mvpListener
     */
    @Override
    public void requestTopArticle(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new TopArticleApi())
                .request(new HttpCallback<HttpData<List<TopArticleEntity>>>(fragment){
                    @Override
                    public void onSucceed(HttpData<List<TopArticleEntity>> topArticleModels) {
                        mvpListener.onSuccess(topArticleModels.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
    }

    /**
     *
     * 请求banner图
     * @param fragment
     * @param mvpListener
     */
    @Override
    public void requestBannerData(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new HomeBannerApi())
                .request(new HttpCallback<HttpData<List<BannerEntity>>>(fragment){

                    @Override
                    public void onSucceed(HttpData<List<BannerEntity>> result){
                        mvpListener.onSuccess(result.getData());

                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });
    }

    /**
     * 公众号查询
     * @param fragment
     * @param mvpListener
     */
    @Override
    public void requestOfficialAccountData(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new HomeOfficialAccountApi())
                .request(new HttpCallback<HttpData<List<OfficialAccountEntity>>>(fragment){

                    @Override
                    public void onSucceed(HttpData<List<OfficialAccountEntity>> result){
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
     * 请求文章数据
     * @param fragment
     * @param page
     * @param mvpListener
     */
    @Override
    public void requestAllHomeArticle(BaseFragment fragment, int page, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new HomeArticleApi()
                        .setPage(page))
                .request(new HttpCallback<HttpData<HomeArticleListEntity>>(fragment){
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
    public void requestUnreadMessage(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new UnreadMessageApi())
                .request(new HttpCallback<HttpData<Integer>>(fragment){

                    @Override
                    public void onSucceed(HttpData<Integer> result){
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
