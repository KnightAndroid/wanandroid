package com.knight.wanandroid.module_feedback.api;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/21 16:27
 * @descript:
 */
public final class FeedBackApi implements IRequestApi {
    private int articleId;


    public FeedBackApi setErrorArticleId(int articleId) {
        this.articleId = articleId;
        return this;
    }

    @Override
    public String getApi() {
        return "aerror/lg/add/json?aid="+articleId +"";
    }
}
