package com.knight.wanandroid.library_network.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 16:38
 * @descript:重命名注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HttpRename {
    /**
     *
     * 默认以字段的名称作为参数名，使用此注解可修改
     * @return
     */
    String value();
}
