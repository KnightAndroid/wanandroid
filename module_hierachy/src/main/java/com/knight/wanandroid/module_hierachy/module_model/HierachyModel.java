package com.knight.wanandroid.module_hierachy.module_model;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyListEntity;
import com.knight.wanandroid.module_hierachy.module_entity.NavigateListEntity;
import com.knight.wanandroid.module_hierachy.module_request.HierachyApi;
import com.knight.wanandroid.module_hierachy.module_request.NavigateApi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 15:41
 * @descript:
 */
public final class HierachyModel implements HierachyContract.HierachyModel {
    @Override
    public void requestHierachyData(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new HierachyApi())
                .request(new HttpCallback<HttpData<ArrayList<HierachyListEntity>>>(fragment){

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

    @Override
    public void requestNavigateData(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new NavigateApi())
                .request(new HttpCallback<HttpData<List<NavigateListEntity>>>(fragment){
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
