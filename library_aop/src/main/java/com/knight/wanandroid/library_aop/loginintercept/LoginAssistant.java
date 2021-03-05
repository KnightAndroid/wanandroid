package com.knight.wanandroid.library_aop.loginintercept;

import android.app.Application;
import android.content.Context;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/4 10:27
 * @descript:
 */
public class LoginAssistant {

    private LoginAssistant(){}

    private static LoginAssistant instance;

    public static LoginAssistant getInstance(){
        if(instance == null){
            synchronized (LoginAssistant.class){
                if(instance == null){
                    instance = new LoginAssistant();
                }
            }
        }

        return instance;

    }

    private ILoginFilter iLoginFilter;
    private Context applicationContext;

    public ILoginFilter getiLoginFilter(){
        return iLoginFilter;
    }

    public void setiLoginFilter(ILoginFilter iLoginFilter){
        this.iLoginFilter = iLoginFilter;
    }


    public void setApplicationContext(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    public Context getApplicationContext(){
        return applicationContext;
    }




}
