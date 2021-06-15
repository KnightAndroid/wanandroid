package com.knight.wanandroid.module_mine.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.module_contract.AppUpdateRecordContract;
import com.knight.wanandroid.module_mine.module_entity.AppUpdateRecordListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/15 16:26
 * @descript:
 */
public class AppUpdateRecordPresenter extends AppUpdateRecordContract.AppUpdateRecordDataPresenter {
    @Override
    public void requestAppVersion() {
        final AppUpdateRecordContract.AppUpdateRecordView mView = getView();
        if (mView == null) {
            return;
        }


        mModel.requestAppRecordVersion((BaseActivity) mView, new MvpListener<AppUpdateRecordListEntity>() {
            @Override
            public void onSuccess(AppUpdateRecordListEntity data) {
                mView.getAppRecordVersion(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }
}
