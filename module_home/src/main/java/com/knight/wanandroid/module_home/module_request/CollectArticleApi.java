package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 20:13
 * @descript:
 */
public class CollectArticleApi implements IRequestApi {

    private int collectArticleId;


    public CollectArticleApi setCollectArticleId(int collectArticleId) {
        this.collectArticleId = collectArticleId;
        return this;
    }

    @Override
    public String getApi() {
        return "lg/collect/"+collectArticleId +"/json";
    }
}
