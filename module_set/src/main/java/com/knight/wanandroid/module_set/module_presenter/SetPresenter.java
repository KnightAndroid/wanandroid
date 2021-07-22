package com.knight.wanandroid.module_set.module_presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_set.module_contract.SetContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/19 16:45
 * @descript:
 */
public class SetPresenter extends SetContract.SetDataPresenter {
    @Override
    public void requestLogout() {
        final SetContract.SetView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestLogout((BaseActivity) mView, new MvpListener() {
            @Override
            public void onSuccess(Object data) {
                mView.logoutSuccess();
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
