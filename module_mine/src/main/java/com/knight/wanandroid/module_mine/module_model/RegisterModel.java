package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.RegisterContract;
import com.knight.wanandroid.module_mine.module_request.RegisterApi;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/22 16:50
 * @descript:
 */
public class RegisterModel implements RegisterContract.RegisterModel {



    @Override
    public void requestRegister(BaseActivity activity, String username,String password,String repassword,MvpListener mvpListener) {
        activity.showLoadingHud("注册中...");
        GoHttp.post(activity)
                .api(new RegisterApi().setUserName(username).setPassword(password).setRepassword(password))
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
