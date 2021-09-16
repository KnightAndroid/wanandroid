package com.knight.wanandroid.module_mine.contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/11 17:45
 * @descript:
 */
public interface AboutContract {


    interface AboutView extends BaseView {
        void setAppVersion(AppUpdateEntity result);
    }

    interface AboutModel extends BaseModel {
        void requestAppVersion(BaseActivity activity, MvpListener mvpListener);
    }

    abstract class AboutDataPresenter extends BasePresenter<AboutModel, AboutView> {
        public abstract void requestAppVersion();

    }
}
