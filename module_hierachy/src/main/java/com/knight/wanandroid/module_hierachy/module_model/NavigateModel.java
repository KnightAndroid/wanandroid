package com.knight.wanandroid.module_hierachy.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_hierachy.module_contract.NavigateContract;
import com.knight.wanandroid.module_hierachy.module_entity.NavigateListEntity;
import com.knight.wanandroid.module_hierachy.module_request.NavigateApi;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/30 14:39
 * @descript:导航model
 */
public class NavigateModel implements NavigateContract.NavigateModel {
    @Override
    public void requestNavigateData(BaseDBActivity activity, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new NavigateApi())
                .request(new HttpCallback<HttpData<List<NavigateListEntity>>>(activity){
                    @Override
                    public void onSucceed(HttpData<List<NavigateListEntity>> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
    }
}
