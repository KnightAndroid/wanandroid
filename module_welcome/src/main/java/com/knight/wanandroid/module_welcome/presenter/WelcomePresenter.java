package com.knight.wanandroid.module_welcome.presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_welcome.contract.WelcomeContract;
import com.knight.wanandroid.module_welcome.entity.AppThemeEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 17:39
 * @descript:获取主题色业务逻辑
 */
public final class WelcomePresenter extends WelcomeContract.WelcomeDataPresenter {
    @Override
    public void requestThemeColor() {
        final WelcomeContract.WelcomeView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestThemeColor((BaseActivity) mView, new MvpListener<AppThemeEntity>() {
            @Override
            public void onSuccess(AppThemeEntity appThemeEntity) {
                mView.setThemeColor(appThemeEntity);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}
