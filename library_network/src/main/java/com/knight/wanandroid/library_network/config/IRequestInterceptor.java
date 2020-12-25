package com.knight.wanandroid.library_network.config;


import com.knight.wanandroid.library_network.data.HttpHeaders;
import com.knight.wanandroid.library_network.data.HttpParams;

/**
 * @author created by luguian
 * @organize wanandroid
 * @Date 2020/12/25 17:28
 * @descript:请求参数拦截器
 */
public interface IRequestInterceptor {
    /**
     * 开始请求之前调用
     *
     * @param url           请求地址
     * @param tag           请求标记
     * @param params        请求参数
     * @param headers       请求头参数
     */
    void intercept(String url, String tag, HttpParams params, HttpHeaders headers);
}
