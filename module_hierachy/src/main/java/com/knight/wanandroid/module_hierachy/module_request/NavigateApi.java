package com.knight.wanandroid.module_hierachy.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/30 14:43
 * @descript:
 */
public final class NavigateApi implements IRequestApi {
    @Override
    public String getApi() {
        return "navi/json";
    }
}
