package com.knight.wanandroid.module_mine.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/21 11:39
 * @descript:个人积分接口
 */
public final class PersonalCoinApi implements IRequestApi {
    @Override
    public String getApi() {
        return "lg/coin/userinfo/json";
    }
}
