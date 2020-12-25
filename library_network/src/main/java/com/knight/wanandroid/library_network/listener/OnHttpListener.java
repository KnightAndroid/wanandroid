package com.knight.wanandroid.library_network.listener;

import okhttp3.Call;

/**
 * @author created by luguian
 * @organize wanandroid
 * @Date 2020/12/25 18:06
 * @descript:请求回调监听器
 */
public interface OnHttpListener<T> {
    /**
     * 请求开始
     */
    default void onStart(Call call) {}

    /**
     * 请求成功
     */
    void onSucceed(T result);

    /**
     * 请求出错
     */
    void onFail(Exception e);

    /**
     * 请求结束
     */
    default void onEnd(Call call) {}
}
