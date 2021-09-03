package com.knight.wanandroid.module_welcome.model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_welcome.contract.WelcomeContract;
import com.knight.wanandroid.module_welcome.entity.AppThemeEntity;
import com.knight.wanandroid.module_welcome.request.GetAppThemeApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 17:36
 * @descript:
 */
public final class WelcomeModel implements WelcomeContract.WelcomeModel {
    @Override
    public void requestThemeColor(BaseActivity baseActivity, MvpListener mvpListener) {
        GoHttp.get(baseActivity)
                .api(new GetAppThemeApi())
                .request(new HttpCallback<HttpData<AppThemeEntity>>(baseActivity){

                    @Override
                    public void onSucceed(HttpData<AppThemeEntity> result){
                        mvpListener.onSuccess(result.getData());

                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
    }
}
