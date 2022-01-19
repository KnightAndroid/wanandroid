package com.knight.wanandroid.module_mine.contract;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_mine.entity.UserInfoCoinEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:09
 * @descript:我的界面合约分包
 */
public interface MineContract {




    interface MineView extends BaseView{
        void setUserInfoCoin(UserInfoCoinEntity userInfoCoinEntity);
        void setUserInfo(UserInfoEntity userInfo);
    }


    interface MineModel extends BaseModel{
        void requestUserInfoCoin(BaseFragment fragment, MvpListener mvpListener);
        void requestUserInfo(BaseFragment fragment, String username, String password, MvpListener mvpListener);

    }

    abstract class MineDataPresenter extends BasePresenter<MineModel,MineView>{
        public abstract void requestUserInfoCoin();
        public abstract void requestUserInfo(String username, String password);

    }
}
