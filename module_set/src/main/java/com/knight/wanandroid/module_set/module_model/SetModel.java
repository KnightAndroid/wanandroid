package com.knight.wanandroid.module_set.module_model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_set.module_api.LogoutApi;
import com.knight.wanandroid.module_set.module_contract.SetContract;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/19 16:38
 * @descript:
 */
public class SetModel implements SetContract.SetModel {


    @Override
    public void requestLogout(BaseActivity baseActivity, MvpListener mvpListener) {
        baseActivity.showLoadingHud("退出登录中...");
        GoHttp.get(baseActivity)
                .api(new LogoutApi())
                .request(new HttpCallback<HttpData>(baseActivity){

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
                        baseActivity.dismissLoadingHud();
                    }

                });
    }
}
