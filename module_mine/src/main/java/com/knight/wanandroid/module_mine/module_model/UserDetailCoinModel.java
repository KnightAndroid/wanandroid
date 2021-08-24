package com.knight.wanandroid.module_mine.module_model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.module_contract.UserDetailCoinContract;
import com.knight.wanandroid.module_mine.module_entity.UserDetailCoinListEntity;
import com.knight.wanandroid.module_mine.module_request.UserDetailCoinApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 11:04
 * @descript:
 */
public final class UserDetailCoinModel implements UserDetailCoinContract.DetailCoinModel {
    @Override
    public void requestUserDetailCoin(BaseActivity activity, int page, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new UserDetailCoinApi().setPage(page))
                .request(new HttpCallback<HttpData<UserDetailCoinListEntity>>(activity){

                    @Override
                    public void onSucceed(HttpData<UserDetailCoinListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });
    }
}
