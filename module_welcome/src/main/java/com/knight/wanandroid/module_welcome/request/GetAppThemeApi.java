package com.knight.wanandroid.module_welcome.request;

import com.knight.wanandroid.library_network.annotation.HttpHeader;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 17:27
 * @descript:
 */
public final class GetAppThemeApi implements IRequestApi {

    @HttpHeader
    private String requestUrl = "customServer";


    @Override
    public String getApi() {
        return "MengSuiXinSuoYuan/wanandroid_server/raw/master/wanandroid_config/config/themeColor.json";
    }
}
