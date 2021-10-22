package com.knight.wanandroid.module_feedback;

import android.app.Application;

import com.knight.wanandroid.library_base.BaseApp;
import com.knight.wanandroid.library_base.listener.AppInit;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/21 16:00
 * @descript:
 */
public final class FeedBackApp extends BaseApp implements AppInit {
    @Override
    protected void initSafeSDK() {

    }

    @Override
    protected void initDangerousSDK() {

    }

    @Override
    public void initModuleApp(Application application) {

    }

    @Override
    public void initModuleData(Application application) {

    }

    @Override
    public void applicationInit(@NonNull Application application) {
        initModuleApp(this);
        initModuleData(this);
    }
}
