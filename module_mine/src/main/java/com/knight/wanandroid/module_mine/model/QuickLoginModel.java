package com.knight.wanandroid.module_mine.model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.contract.QuickLoginContract;
import com.knight.wanandroid.module_mine.request.LoginApi;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/22 19:07
 * @descript:
 */
public final class QuickLoginModel implements QuickLoginContract.QuickLoginModel {

    /**
     *
     * 登录请求
     * @param activity
     * @param username
     * @param password
     * @param mvpListener
     */
    @Override
    public void requestUserInfo(BaseActivity activity, String username, String password, MvpListener mvpListener) {
        activity.showLoadingHud(activity.getString(R.string.mine_login_request));
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
