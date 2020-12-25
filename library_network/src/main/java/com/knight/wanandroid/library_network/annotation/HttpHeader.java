package com.knight.wanandroid.library_network.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 16:36
 * @descript:请求头注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HttpHeader {
}
