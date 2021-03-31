package com.knight.wanandroid.library_aop.clickintercept;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/5 10:13
 * @descript:双击切面 用法@DoubleClick
 */

@Aspect
public class DoubleClickAspect {

    final String TAG = DoubleClickAspect.class.getSimpleName();
    private boolean isDoubleClick = false;



    @Before("execution(@com.knight.wanandroid.library_aop.clickintercept.DoubleClick * * (..))")
    public void beforeEnableDoubleClick(JoinPoint joinPoint) throws Throwable{
         isDoubleClick = true;
    }


    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClickListener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        if(isDoubleClick || !NoDoubbleClickUtils.isDoubleClick()){
             proceedingJoinPoint.proceed();
             isDoubleClick = false;
        }
    }








}
