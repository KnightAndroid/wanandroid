package com.knight.wanandroid.module_home.module_model;

import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.EveryDayPushArticlesEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;
import com.knight.wanandroid.module_home.module_request.AppCheckUpdateApi;
import com.knight.wanandroid.module_home.module_request.EveryDayPushArticleApi;
import com.knight.wanandroid.module_home.module_request.HomeBannerApi;
import com.knight.wanandroid.module_home.module_request.HomeOfficialAccountApi;
import com.knight.wanandroid.module_home.module_request.TopArticleApi;
import com.knight.wanandroid.module_home.module_request.UnreadMessageApi;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:25
 * @descript:
 */
public class HomeModel implements HomeContract.HomeModel {


    /**
     *
     * 请求置顶文章数据
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
     * 请求轮播图数据
     * @param fragment
     * @param mvpListener
     */
    @Override
    public void requestBannerData(BaseFragment fragment, final MvpListener mvpListener) {
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

    @Override
    public void requestAppUpdateMessage(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new AppCheckUpdateApi())
                .request(new HttpCallback<HttpData<AppUpdateEntity>>(fragment){

                    @Override
                    public void onSucceed(HttpData<AppUpdateEntity> result){
                        mvpListener.onSuccess(result.getData());

                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
    }

    @Override
    public void requestEveryDayPushArticle(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new EveryDayPushArticleApi())
                .request(new HttpCallback<HttpData<EveryDayPushArticlesEntity>>(fragment){

                    @Override
                    public void onSucceed(HttpData<EveryDayPushArticlesEntity> result){
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


}
