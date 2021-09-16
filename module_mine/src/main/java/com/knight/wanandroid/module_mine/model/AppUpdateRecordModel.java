package com.knight.wanandroid.module_mine.model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.contract.AppUpdateRecordContract;
import com.knight.wanandroid.module_mine.entity.AppUpdateRecordListEntity;
import com.knight.wanandroid.module_mine.request.AppUpdateRecordApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/15 16:19
 * @descript:
 */
public final class AppUpdateRecordModel implements AppUpdateRecordContract.AppUpdateRecordModel {
    @Override
    public void requestAppRecordVersion(BaseActivity activity, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new AppUpdateRecordApi())
                .request(new HttpCallback<HttpData<AppUpdateRecordListEntity>>(activity){

                    @Override
                    public void onSucceed(HttpData<AppUpdateRecordListEntity> result){
                        mvpListener.onSuccess(result.getData());

                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
    }
}
