package com.knight.wanandroid.library_common.provider;

import android.app.Application;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 11:20
 * @descript:application提供
 */
public class ApplicationProvider {
    private Application app;
    private static ApplicationProvider instance;

    public ApplicationProvider (Application app) {
        this.app = app;
    }

    public static void init (Application app) {
        if (instance == null) {
            instance = new ApplicationProvider(app);
        }
    }

    /**
     *
     * 获取本App的application
     * @return
     */
    public static ApplicationProvider getInstance() {
        if (instance == null) {
            synchronized (ApplicationProvider.class) {
                if (null == instance) {
                    instance = new ApplicationProvider(AppBridge.getApplicationByReflect());
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        return app;
    }



}
