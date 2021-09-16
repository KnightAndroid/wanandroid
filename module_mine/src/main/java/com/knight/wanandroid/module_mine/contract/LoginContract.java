package com.knight.wanandroid.module_mine.contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/19 17:53
 * @descript:
 */
public interface LoginContract {

    interface LoginView extends BaseView {

        void setUserInfo(UserInfoEntity userInfo);


    }
    interface LoginModel extends BaseModel{
        void requestUserInfo(BaseActivity activity, String username,String password,MvpListener mvpListener);


    }

    abstract class LoginDataPresenter extends BasePresenter<LoginModel,LoginView>{
        public abstract void requestUserInfo(String username, String password);
    }

}
