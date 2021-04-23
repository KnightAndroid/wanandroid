package com.knight.wanandroid.module_mine.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.module_contract.CoinRankContract;
import com.knight.wanandroid.module_mine.module_entity.CoinRankListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 17:19
 * @descript:积分排行榜presenter
 */
public class CoinRankPresenter extends CoinRankContract.CoinRankDataPresenter {
    @Override
    public void requestRankCoin(BaseActivity activity, int page) {
        final CoinRankContract.CoinRankView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestRankCoin(activity, page, new MvpListener<CoinRankListEntity>() {
            @Override
            public void onSuccess(CoinRankListEntity data) {
                mView.setRankCoin(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);

            }
        });


    }
}
