package com.knight.wanandroid.library_network.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.TypeAdapters;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/30 17:46
 * @descript:
 */
public final class GsonFactory {

    private static volatile Gson sGson;

    private GsonFactory() {}

    /**
     * 获取单例的 Gson 对象
     */
    public static Gson getSingletonGson() {
        // 加入双重校验锁
        if(sGson == null) {
            synchronized (GsonFactory.class) {
                if(sGson == null){
                    sGson = createGsonBuilder().create();
                }
            }
        }
        return sGson;
    }

    /**
     * 创建 Gson 构建器
     */
    public static GsonBuilder createGsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(TypeAdapters.newFactory(String.class, new StringTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(boolean.class, Boolean.class, new BooleanTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, new IntegerTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, new LongTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, new FloatTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, new DoubleTypeAdapter()))
                .registerTypeHierarchyAdapter(List.class, new ListTypeAdapter());
    }
}
