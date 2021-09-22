package com.knight.wanandroid.module_mine.contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wananadroid
 * @Date 2021/9/22 18:59
 * @descript:
 */
public interface QuickLoginContract {


    interface QuickLoginView extends BaseView {
        void setUserInfo(UserInfoEntity userInfo);
    }

    interface QuickLoginModel extends BaseModel {
        void requestUserInfo(BaseActivity activity, String username, String password, MvpListener mvpListener);
    }

    abstract class QuickLoginDataPresenter extends BasePresenter<QuickLoginModel,QuickLoginView> {
        public abstract void requestUserInfo(String username, String password);
    }


}
