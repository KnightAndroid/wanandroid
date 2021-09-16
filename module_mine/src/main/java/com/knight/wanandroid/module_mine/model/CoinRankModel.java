package com.knight.wanandroid.module_mine.model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_mine.contract.CoinRankContract;
import com.knight.wanandroid.module_mine.entity.CoinRankListEntity;
import com.knight.wanandroid.module_mine.request.RankCoinApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 17:11
 * @descript:排行榜请求model
 */
public final class CoinRankModel implements CoinRankContract.CoinRankModel {
    @Override
    public void requestRankCoin(BaseActivity activity, int page, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new RankCoinApi().setPage(page))
                .request(new HttpCallback<HttpData<CoinRankListEntity>>(activity){

                    @Override
                    public void onSucceed(HttpData<CoinRankListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }
                });

    }
}
