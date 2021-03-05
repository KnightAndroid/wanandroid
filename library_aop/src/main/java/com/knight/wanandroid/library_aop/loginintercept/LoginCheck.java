package com.knight.wanandroid.library_aop.loginintercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/3 17:59
 * @descript:拦截登录注解
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
    boolean isSkipLogin() default false;//增加额外的信息，决定要不要跳过检查，默认跳过
}
