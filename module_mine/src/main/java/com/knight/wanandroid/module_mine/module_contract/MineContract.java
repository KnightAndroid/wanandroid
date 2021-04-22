package com.knight.wanandroid.module_mine.module_contract;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_mine.module_entity.UserInfoCoinEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:09
 * @descript:我的界面合约分包
 */
public interface MineContract {



    interface MineView extends BaseView{
        void setUserInfoCoin(UserInfoCoinEntity userInfoCoinEntity);
        void logoutSuccess();
    }


    interface MineModel extends BaseModel{
        void requestUserInfoCoin(BaseDBActivity activity, MvpListener mvpListener);
        void requestLogout(BaseDBActivity activity,MvpListener mvpListener);

    }

    abstract class MineDataPresenter extends BasePresenter<MineModel,MineView>{
        public abstract void requestUserInfoCoin(BaseDBActivity activity);
        public abstract void requestLogout(BaseDBActivity activity);

    }
}
