package com.knight.wanandroid.library_util;

import java.lang.reflect.ParameterizedType;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 20:33
 * @descript:内部获取第i个参数的真实类型,反射new对象
 */
public class CreateUtils {


    public static <T> T get(Object o,int i){
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
