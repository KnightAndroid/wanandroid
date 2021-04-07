package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/7 16:21
 * @descript:首页顶部banner图
 */
public class HomeBannerApi implements IRequestApi {
    @Override
    public String getApi() {
        return "banner/json";
    }
}
