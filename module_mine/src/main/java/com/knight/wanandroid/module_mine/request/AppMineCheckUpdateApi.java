package com.knight.wanandroid.module_mine.request;

import com.knight.wanandroid.library_network.annotation.HttpHeader;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/11 11:32
 * @descript:
 */
public final class AppMineCheckUpdateApi implements IRequestApi {

    @HttpHeader
    private String requestUrl = "customServer";




    @Override
    public String getApi() {
        return "MengSuiXinSuoYuan/wanandroid_server/raw/master/wanandroid_config/update/update.json";
    }
}
