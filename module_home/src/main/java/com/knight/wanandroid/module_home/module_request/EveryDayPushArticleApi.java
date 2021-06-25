package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.annotation.HttpHeader;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/16 15:17
 * @descript:
 */
public class EveryDayPushArticleApi implements IRequestApi {

    @HttpHeader
    private String requestUrl = "customServer";
    @Override
    public String getApi() {
        return "MengSuiXinSuoYuan/wanandroid_server/raw/master/wanandroid_config/advert/dailyPushArticles.json";
    }
}
