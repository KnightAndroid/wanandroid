package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:41
 * @descript:
 */
public class HomeArticleApi implements IRequestApi {
    @Override
    public String getApi() {
        return "article/list/0/json";
    }
}
