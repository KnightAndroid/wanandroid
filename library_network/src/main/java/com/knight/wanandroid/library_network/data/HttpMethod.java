package com.knight.wanandroid.library_network.data;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:40
 * @descript:请求方式
 */
public enum HttpMethod {

    /** GET 请求 */
    GET("GET"),

    /** Post 请求 */
    POST("POST"),

    /** Head 请求 */
    HEAD("HEAD"),

    /** Delete 请求 */
    DELETE("DELETE"),

    /** Put 请求 */
    PUT("PUT"),

    /** Patch 请求 */
    PATCH("PATCH");

    /** 请求方式 */
    private final String mMethod;

    HttpMethod(String method) {
        this.mMethod = method;
    }

    @NonNull
    @Override
    public String toString() {
        return mMethod;
    }
}
