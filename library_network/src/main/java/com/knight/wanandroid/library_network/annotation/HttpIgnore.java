package com.knight.wanandroid.library_network.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 16:37
 * @descript:忽略注解，参数不会发送到后台
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HttpIgnore {
}
