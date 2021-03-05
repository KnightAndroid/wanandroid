package com.knight.wanandroid.library_aop.loginintercept;

import android.content.Context;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/4 9:24
 * @descript:AspectJ登录拦截代码
 */
@Aspect
public class LoginCheckAspect {

    private static final String TAG = "LoginCheckAspect";
    @Pointcut("execution(@com.knight.wanandroid.library_aop.loginintercept.LoginCheck * * (..))")
    public void LoginCheck(){}



    @Around("LoginCheck()")
    public void aroundLoginPoint(ProceedingJoinPoint joinPoint) throws Throwable{

        //获取用户实现的ILogin类，如果没有调用init设置初始化就抛出异常
        ILoginFilter iLoginFilter = LoginAssistant.getInstance().getiLoginFilter();
        if(iLoginFilter == null){
            throw new RuntimeException("LoginManager没有初始化");
        }

        //先得到方法得签名methodSignature,然后得到@LoginFilter注解，如果注解为空，就不再往下走
        Signature signature = joinPoint.getSignature();
        if(!(signature instanceof MemberSignature)){
            throw new RuntimeException("该注解只能用于方法上");
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        LoginCheck loginCheck = methodSignature.getMethod().getAnnotation(LoginCheck.class);
        if(loginCheck == null){
            return;
        }

        Context mContext = LoginAssistant.getInstance().getApplicationContext();

        boolean skipLogin = loginCheck.isSkipLogin();
        if(skipLogin){
            //可以跳过登录
            joinPoint.proceed();
        } else {
            if(iLoginFilter.isLogin(mContext)){
                joinPoint.proceed();
            } else {
                //提示
                iLoginFilter.login(mContext,loginCheck.isSkipLogin());
            }

        }


    }



}
