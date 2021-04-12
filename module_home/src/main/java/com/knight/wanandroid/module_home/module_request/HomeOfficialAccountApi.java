package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 15:03
 * @descript:获取公众号列表地址
 */
public class HomeOfficialAccountApi implements IRequestApi {

    @Override
    public String getApi() {
        return "wxarticle/chapters/json";
    }
}
