package com.knight.wanandroid.library_aop.clickintercept;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/4 17:03
 * @descript:
 */

@Aspect
public class SingleClickAspect {
    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClickListener(ProceedingJoinPoint joinPoint) throws Throwable{
        if(!NoDoubbleClickUtils.isDoubleClick()){
            try{
                joinPoint.proceed();
            } catch (Throwable throwable){
                throwable.printStackTrace();
            }
        }
    }
}
