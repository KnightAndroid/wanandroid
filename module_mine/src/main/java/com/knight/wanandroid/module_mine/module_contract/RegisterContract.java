package com.knight.wanandroid.module_mine.module_contract;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/22 16:25
 * @descript:
 */
public interface  RegisterContract {

        interface RegisterView extends BaseView{
            void setUserInfo(UserInfoEntity userInfo);
        }

        interface RegisterModel extends BaseModel{
            void requestRegister(BaseActivity activity, String username,String password,String repassword,MvpListener mvpListener);
        }

        abstract class RegisterDataPresenter extends BasePresenter<RegisterModel,RegisterView>{
            public abstract void requestRegister(BaseActivity activity,String username,String password,String repassword);
        }

}
