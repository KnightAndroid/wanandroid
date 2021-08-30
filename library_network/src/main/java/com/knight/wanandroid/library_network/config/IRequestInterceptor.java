package com.knight.wanandroid.library_network.config;


import com.knight.wanandroid.library_network.data.HttpHeaders;
import com.knight.wanandroid.library_network.data.HttpParams;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:28
 * @descript:请求参数拦截器
 */
public interface IRequestInterceptor {
    /**
     * 拦截参数
     *
     * @param api           接口对象
     * @param params        请求参数
     * @param headers       请求头参数
     */
    void interceptArguments(IRequestApi api, HttpParams params, HttpHeaders headers);
}
