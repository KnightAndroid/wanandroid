package com.knight.wanandroid.module_mine.contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_mine.entity.CoinRankListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 17:03
 * @descript:
 */
public interface CoinRankContract {

        interface CoinRankView extends BaseView {
            void setRankCoin(CoinRankListEntity result);
        }

        interface CoinRankModel extends BaseModel {
            void requestRankCoin(BaseActivity activity, int page, MvpListener mvpListener);
        }

        abstract class CoinRankDataPresenter extends BasePresenter<CoinRankModel,CoinRankView>{
            public abstract void requestRankCoin(int page);

        }
}
