package com.knight.wanandroid.module_set.module_api;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/22 10:34
 * @descript:
 */
public final class LogoutApi implements IRequestApi {


    @Override
    public String getApi() {
        return "user/logout/json";
    }
}
