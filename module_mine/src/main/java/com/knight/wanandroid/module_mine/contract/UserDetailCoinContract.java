package com.knight.wanandroid.module_mine.contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_mine.entity.UserDetailCoinListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 10:52
 * @descript:
 */
public interface UserDetailCoinContract {


    interface DetailCoinView extends BaseView{
        void setUserDetailCoin(UserDetailCoinListEntity result);
    }

    interface DetailCoinModel extends BaseModel{
        void requestUserDetailCoin(BaseActivity activity, int page, MvpListener mvpListener);
    }

    abstract class DetailCoinDataPresenter extends BasePresenter<DetailCoinModel,DetailCoinView>{
        public abstract void requestUserDetailCoin(int page);
    }



}
