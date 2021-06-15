package com.knight.wanandroid.module_mine.module_request;

import com.knight.wanandroid.library_network.annotation.HttpHeader;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/15 16:22
 * @descript:
 */
public class AppUpdateRecordApi implements IRequestApi {
    @HttpHeader
    private String requestUrl = "customServer";



    @Override
    public String getApi() {
        return "MengSuiXinSuoYuan/wanandroid_server/raw/master/wanandroid_config/update/versionrecord.json";
    }
}
