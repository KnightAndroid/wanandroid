package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.MineContract;
import com.knight.wanandroid.module_mine.module_entity.UserInfoCoinEntity;
import com.knight.wanandroid.module_mine.module_request.LogoutApi;
import com.knight.wanandroid.module_mine.module_request.PersonalCoinApi;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:12
 * @descript:
 */
public class MineModel implements MineContract.MineModel {


    @Override
    public void requestUserInfoCoin(BaseDBActivity activity, MvpListener mvpListener) {
        activity.showLoadingHud("个人信息获取中...");
        GoHttp.get(activity)
                .api(new PersonalCoinApi())
                .request(new HttpCallback<HttpData<UserInfoCoinEntity>>(activity) {
                    @Override
                    public void onSucceed(HttpData<UserInfoCoinEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mvpListener.onError(e.getMessage());
                    }

                    @Override
                    public void onEnd(Call call){
                        activity.dismissLoadingHud();
                    }
                });

    }

    @Override
    public void requestLogout(BaseDBActivity activity, MvpListener mvpListener) {
        activity.showLoadingHud("登录请求中...");
        GoHttp.get(activity)
                .api(new LogoutApi())
                .request(new HttpCallback<HttpData>(activity){

                    @Override
                    public void onSucceed(HttpData httpData){
                        mvpListener.onSuccess(httpData);

                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                    @Override
                    public void onEnd(Call call){
                        activity.dismissLoadingHud();
                    }

                });
    }

}
