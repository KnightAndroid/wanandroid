package com.knight.wanandroid.library_network.config;

import com.knight.wanandroid.library_network.HttpConfig;

import okhttp3.OkHttpClient;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/30 15:25
 * @descript:
 */
public interface IRequestClient {

    /**
     * 获取 OkHttpClient
     */
    default OkHttpClient getClient() {
        return HttpConfig.getInstance().getClient();
    }
}
