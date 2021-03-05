package com.knight.wanandroid.library_aop.loginintercept;

import android.content.Context;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/4 16:28
 * @descript:登录管理器
 */
public class LoginManager {


      private static LoginManager instance;
      private LoginManager(){}

      public static LoginManager getInstance(){
          if(null == instance){
              synchronized (LoginManager.class){
                  if(null == instance){
                      instance = new LoginManager();
                  }
              }
          }

          return instance;
      }

      public void init(Context context,ILoginFilter iLoginFilter){
          LoginAssistant.getInstance().setApplicationContext(context);
          LoginAssistant.getInstance().setiLoginFilter(iLoginFilter);
      }


}
