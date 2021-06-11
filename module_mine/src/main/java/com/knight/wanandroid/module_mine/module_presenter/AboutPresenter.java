package com.knight.wanandroid.module_mine.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_mine.module_contract.AboutContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/11 17:50
 * @descript:
 */
public class AboutPresenter extends AboutContract.AboutDataPresenter {
    @Override
    public void requestAppVersion() {
        final AboutContract.AboutView mView = getView();
        if (mView == null) {
            return;
        }


        mModel.requestAppVersion((BaseActivity) mView, new MvpListener<AppUpdateEntity>() {
            @Override
            public void onSuccess(AppUpdateEntity appUpdateEntity) {
                mView.setAppVersion(appUpdateEntity);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });


    }
}
