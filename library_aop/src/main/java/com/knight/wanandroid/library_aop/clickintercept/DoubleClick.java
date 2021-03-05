package com.knight.wanandroid.library_aop.clickintercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/5 9:56
 * @descript:自定义pointcuts 可以双击的事件
 */

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR,ElementType.METHOD})
public @interface DoubleClick {
}
