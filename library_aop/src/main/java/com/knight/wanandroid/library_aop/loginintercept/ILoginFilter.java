package com.knight.wanandroid.library_aop.loginintercept;

import android.content.Context;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/4 10:04
 * @descript:登录过滤
 */
public interface ILoginFilter {
    void login(Context applicationContext,boolean loginDefine);
    boolean isLogin(Context applicationContext);
    void clearLoginStatus(Context applicationContext);
}
