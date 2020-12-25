package com.knight.wanandroid.library_network.config;

import com.knight.wanandroid.library_network.annotation.HttpIgnore;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:33
 * @descript: 请求接口简单配置类
 */
public final class RequestApi implements IRequestApi {

    /**
     * 接口地址
     */
    @HttpIgnore
    private String mApi;

    public RequestApi(String api) {
        mApi = api;
    }

    @Override
    public String getApi() {
        return mApi;
    }

    @Override
    public String toString() {
        return mApi;
    }
}
