package com.knight.wanandroid.module_hierachy.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyListEntity;
import com.knight.wanandroid.module_hierachy.module_request.HierachyApi;

import java.util.ArrayList;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 15:41
 * @descript:
 */
public class HierachyModel implements HierachyContract.HierachyModel {
    @Override
    public void requestHierachyData(BaseDBActivity activity, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new HierachyApi())
                .request(new HttpCallback<HttpData<ArrayList<HierachyListEntity>>>(activity){

                    @Override
                    public void onSucceed(HttpData<ArrayList<HierachyListEntity>> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });
    }
}
