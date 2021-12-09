package com.knight.wanandroid.module_mine.model;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.contract.MineContract;
import com.knight.wanandroid.module_mine.entity.UserInfoCoinEntity;
import com.knight.wanandroid.module_mine.request.LoginApi;
import com.knight.wanandroid.module_mine.request.PersonalCoinApi;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:12
 * @descript:
 */
public final class MineModel implements MineContract.MineModel {

    /**
     *
     * 获取金币信息
     * @param fragment
     * @param mvpListener
     */
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

    /**
     *
     * 请求登录
     * @param fragment
     * @param username
     * @param password
     * @param mvpListener
     */
    @Override
    public void requestUserInfo(BaseFragment fragment, String username, String password, MvpListener mvpListener) {
        fragment.showLoadingHud(fragment.getString(R.string.base_login_request));
        GoHttp.post(fragment)
                .api(new LoginApi().setUserName(username).setPassword(password))
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
