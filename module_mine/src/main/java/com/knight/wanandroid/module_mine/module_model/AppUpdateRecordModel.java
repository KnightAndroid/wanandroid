package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.AppUpdateRecordContract;
import com.knight.wanandroid.module_mine.module_entity.AppUpdateRecordListEntity;
import com.knight.wanandroid.module_mine.module_request.AppUpdateRecordApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/15 16:19
 * @descript:
 */
public class AppUpdateRecordModel implements AppUpdateRecordContract.AppUpdateRecordModel {
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
