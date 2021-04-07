package com.knight.wanandroid.module_home.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.BannerModel;
import com.knight.wanandroid.module_home.module_request.HomeBannerApi;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:25
 * @descript:
 */
public class HomeModel implements HomeContract.HomeModel {


    //请求数据
    @Override
    public void requestBannerData(BaseDBActivity activity, final MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new HomeBannerApi())
                .request(new HttpCallback<HttpData<List<BannerModel>>>(activity){

                    @Override
                    public void onSucceed(HttpData<List<BannerModel>> result){
                         mvpListener.onSuccess(result.getData());

                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });


        }
}
