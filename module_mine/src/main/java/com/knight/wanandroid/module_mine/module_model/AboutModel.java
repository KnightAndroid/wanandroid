package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.AboutContract;
import com.knight.wanandroid.module_mine.module_request.AppMineCheckUpdateApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/11 17:47
 * @descript:
 */
public final class AboutModel implements AboutContract.AboutModel {
    @Override
    public void requestAppVersion(BaseActivity activity, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new AppMineCheckUpdateApi())
                .request(new HttpCallback<HttpData<AppUpdateEntity>>(activity){

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
}
