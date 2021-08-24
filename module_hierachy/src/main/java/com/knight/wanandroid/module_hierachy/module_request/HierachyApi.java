package com.knight.wanandroid.module_hierachy.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 15:39
 * @descript:
 */
public final class HierachyApi implements IRequestApi {

    @Override
    public String getApi() {
        return "tree/json";
    }
}
