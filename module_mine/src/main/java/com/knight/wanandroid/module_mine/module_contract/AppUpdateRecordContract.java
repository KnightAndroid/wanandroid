package com.knight.wanandroid.module_mine.module_contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_mine.module_entity.AppUpdateRecordListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/15 16:09
 * @descript:
 */
public interface AppUpdateRecordContract {

    interface AppUpdateRecordView extends BaseView {
          void getAppRecordVersion(AppUpdateRecordListEntity appUpdateRecordListEntity);
    }

    interface AppUpdateRecordModel extends BaseModel{
        void requestAppRecordVersion(BaseActivity activity, MvpListener mvpListener);
    }

    abstract class AppUpdateRecordDataPresenter extends BasePresenter<AppUpdateRecordModel, AppUpdateRecordContract.AppUpdateRecordView> {
        public abstract void requestAppVersion();
    }
}
