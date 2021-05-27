package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 17:11
 * @descript: Activity 管理
 */
public class ActivityManagerUtils implements Application.ActivityLifecycleCallbacks{
   private static volatile  ActivityManagerUtils instance;


   private ArrayMap<String,Activity> mActivityArrayMap = new ArrayMap<>();



   private String mCurrentTag;

   private ActivityManagerUtils(){}


    /**
     * 单例模式
     * 双重校验锁
     * @return
     */
   public static ActivityManagerUtils getInstance(){
       if(instance == null) {
           synchronized (ActivityManagerUtils.class) {
               if(instance == null){
                   instance = new ActivityManagerUtils();
               }
           }
       }
       return instance;
   }




    /**
     *
     * 获取栈顶的Activity
     * @return
     */
    public Activity getTopActivity(){
        return mActivityArrayMap.get(mCurrentTag);
    }

    /**
     *
     * 销毁所有 Activity
     *
     */
    public void finishAllActivity(){
        String[] keys = mActivityArrayMap.keySet().toArray(new String[]{});
        for(String key : keys) {
            Activity activity = mActivityArrayMap.get(key);
            activity.finish();
            mActivityArrayMap.remove(key);
        }
    }

    @SafeVarargs
    public final void finishAllActivities(Class<? extends Activity>...classArray){
         String[] keys = mActivityArrayMap.keySet().toArray(new String[]{});
         for(String key : keys){
             Activity activity = mActivityArrayMap.get(key);
             if(activity != null && !activity.isFinishing()){
                 boolean whiteClazz = false;
                 if(classArray != null){
                     for(Class<? extends Activity> clazz : classArray){
                         if(activity.getClass() == clazz){
                             whiteClazz = true;
                         }
                     }
                 }


                 //如果不是白名单上的Activity就销毁掉
                 if(!whiteClazz){
                     activity.finish();
                     mActivityArrayMap.remove(key);
                 }

             }

         }

    }

    /**
     *
     * 获取一个独一无二的对象标记
     * @param object
     * @return
     */
    private static String getObjectTag(Object object){
        //对象所在的包名 + 对象的内存hash 地址
        return object.getClass().getName() + Integer.toHexString(object.hashCode());
    }





    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
          mCurrentTag = getObjectTag(activity);
          mActivityArrayMap.put(getObjectTag(activity),activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
         mCurrentTag = getObjectTag(activity);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
         mCurrentTag = getObjectTag(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivityArrayMap.remove(getObjectTag(activity));
        if(getObjectTag(activity).equals(mCurrentTag)){
            //清除当前标记
            mCurrentTag = null;

        }
    }
}
