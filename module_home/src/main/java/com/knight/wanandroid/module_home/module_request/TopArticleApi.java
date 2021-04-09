package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 10:08
 * @descript:顶部文章数据
 */
public class TopArticleApi implements IRequestApi {
    @Override
    public String getApi() {
        return "article/top/json";
    }
}
