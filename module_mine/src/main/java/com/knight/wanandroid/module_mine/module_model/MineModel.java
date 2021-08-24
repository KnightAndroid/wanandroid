package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.MineContract;
import com.knight.wanandroid.module_mine.module_entity.UserInfoCoinEntity;
import com.knight.wanandroid.module_mine.module_request.PersonalCoinApi;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:12
 * @descript:
 */
public final class MineModel implements MineContract.MineModel {


    @Override
    public void requestUserInfoCoin(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new PersonalCoinApi())
                .request(new HttpCallback<HttpData<UserInfoCoinEntity>>(fragment) {
                    @Override
                    public void onSucceed(HttpData<UserInfoCoinEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mvpListener.onError(e.getMessage());
                    }

                    @Override
                    public void onEnd(Call call){
                        
                    }
                });

    }


}
