package com.knight.wanandroid.module_mine.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 17:24
 * @descript:
 */
public class MyCollectArticleApi implements IRequestApi {
    private int page;


    public MyCollectArticleApi setPage(int page){
        this.page = page;
        return this;

    }

    @Override
    public String getApi() {
        return "lg/collect/list/"+ page +"/json";
    }
}
