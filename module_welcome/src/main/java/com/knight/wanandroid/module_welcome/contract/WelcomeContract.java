package com.knight.wanandroid.module_welcome.contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_welcome.entity.AppThemeEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 17:30
 * @descript:欢迎模块请求
 */
public interface WelcomeContract {
    interface WelcomeView extends BaseView {


        //获取主题色
        void setThemeColor(AppThemeEntity appThemeEntity);
    }


    interface WelcomeModel extends BaseModel {

        //请求主题色
        void requestThemeColor(BaseActivity baseActivity,MvpListener mvpListener);

    }

    abstract class WelcomeDataPresenter extends BasePresenter<WelcomeModel,WelcomeView> {
        //具体实现
        public abstract void requestThemeColor();

    }
}
