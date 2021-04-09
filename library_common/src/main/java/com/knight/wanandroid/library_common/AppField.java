package com.knight.wanandroid.library_common;

import android.app.Application;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 11:30
 * @descript:反射得到application
 */
public class AppField {

    static final AppField INSTANCE = new AppField();

    Application getApplicationByReflect () {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object thread = getActivityThread();
            Object app = activityThreadClass.getMethod("getApplication").invoke(thread);
            if (app == null) {
                return null;
            }
            return (Application) app;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }



    private Object getActivityThread () {
        Object activityThread = getActivityThreadInActivityThreadStaticField();
        if (activityThread != null) {
            return activityThread;
        }
        activityThread = getActivityThreadInActivityThreadStaticMethod();
        if (activityThread != null) {
            return activityThread;
        }
        return getActivityThreadInLoadedApkField();


    }



    private Object getActivityThreadInActivityThreadStaticField () {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            return currentActivityThreadField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Object getActivityThreadInActivityThreadStaticMethod () {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            return activityThreadClass.getMethod("currentActivityThread").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Object getActivityThreadInLoadedApkField () {
        try {
            Field mLoadedApkField = Application.class.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(getApplicationByReflect());
            Field mActivityThreadField = mLoadedApk.getClass().getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            return mActivityThreadField.get(mLoadedApk);
        } catch (Exception e) {
            return null;
        }
    }





}
