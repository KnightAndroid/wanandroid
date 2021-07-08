package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.LoginContract;
import com.knight.wanandroid.module_mine.module_request.LoginApi;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/19 18:11
 * @descript:
 */
public class LoginModel implements LoginContract.LoginModel {


    @Override
    public void requestUserInfo(BaseActivity activity, String username, String password, MvpListener mvpListener) {
        activity.showLoadingHud("登录请求中...");
        GoHttp.post(activity)
                .api(new LoginApi().setUserName(username).setPassword(password))
                .request(new HttpCallback<HttpData<UserInfoEntity>>(activity){
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
                            activity.dismissLoadingHud();
                    }


                });

    }
}
