package com.knight.wanandroid.module_utils.model;

import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_utils.contract.UtilsContract;
import com.knight.wanandroid.module_utils.entity.UtilsEntity;
import com.knight.wanandroid.module_utils.request.UtilsApi;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/12 14:54
 * Description:UtilsModel
 */
public final class UtilsModel implements UtilsContract.UtilsModel{
    @Override
    public void requestUtilLists(BaseActivity activity, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new UtilsApi())
                .request(new HttpCallback<HttpData<List<UtilsEntity>>>(activity){
                    @Override
                    public void onSucceed(HttpData<List<UtilsEntity>> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }
}
