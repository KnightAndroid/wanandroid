package com.knight.wanandroid.module_set.module_contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/19 16:32
 * @descript:
 */
public interface SetContract {
    interface SetView extends BaseView {
        void logoutSuccess();
    }


    interface SetModel extends BaseModel {
        void requestLogout(BaseActivity baseActivity, MvpListener mvpListener);

    }

    abstract class SetDataPresenter extends BasePresenter<SetModel,SetView> {
        public abstract void requestLogout();

    }
}
