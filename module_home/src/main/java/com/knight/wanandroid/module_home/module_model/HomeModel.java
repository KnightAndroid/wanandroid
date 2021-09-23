package com.knight.wanandroid.module_home.module_model;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.EveryDayPushArticlesEntity;
import com.knight.wanandroid.module_home.module_request.AppCheckUpdateApi;
import com.knight.wanandroid.module_home.module_request.EveryDayPushArticleApi;
import com.knight.wanandroid.module_home.module_request.HomeLoginApi;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:25
 * @descript:
 */
public final class HomeModel implements HomeContract.HomeModel {

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
    public void requestUserInfo(BaseFragment fragment, String username, String password, MvpListener mvpListener) {
        fragment.showLoadingHud(fragment.getString(R.string.base_login_request));
        GoHttp.post(fragment)
                .api(new HomeLoginApi().setUserName(username).setPassword(password))
                .request(new HttpCallback<HttpData<UserInfoEntity>>(fragment){
                    @Override
                    public void onSucceed(HttpData<UserInfoEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                    @Override
                    public void onEnd(Call call){
                        fragment.dismissLoadingHud();
                    }


                });
    }


}
