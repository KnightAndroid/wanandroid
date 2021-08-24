package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:41
 * @descript:
 */
public final class HomeArticleApi implements IRequestApi {


    private int page;

    public int getPage() {
        return page;
    }

    public HomeArticleApi setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public String getApi() {
        return "article/list/"+page+"/json";
    }


}
