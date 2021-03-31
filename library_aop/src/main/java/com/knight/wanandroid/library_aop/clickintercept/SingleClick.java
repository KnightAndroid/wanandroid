package com.knight.wanandroid.library_aop.clickintercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/4 16:45
 * @descript:双击屏蔽
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {


    int Value() default 2000;
    int[] except() default {};

    String[] exceptIdName() default {};

}
