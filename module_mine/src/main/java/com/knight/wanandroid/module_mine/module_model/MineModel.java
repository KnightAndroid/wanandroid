package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
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
    public void requestUserInfoCoin(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new PersonalCoinApi())
                .request(new HttpCallback<HttpData<UserInfoCoinEntity>>(fragment) {
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
                        
                    }
                });

    }

    @Override
    public void requestLogout(BaseFragment fragment, MvpListener mvpListener) {
        ((BaseDBActivity)fragment.getActivity()).showLoadingHud("登录请求中...");
        GoHttp.get(fragment)
                .api(new LogoutApi())
                .request(new HttpCallback<HttpData>(fragment){

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
                        ((BaseDBActivity)fragment.getActivity()).dismissLoadingHud();
                    }

                });
    }

}
