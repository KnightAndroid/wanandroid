package com.knight.wanandroid.module_mine.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/25 16:39
 * @descript:
 */
public class DeleteShareArticleApi implements IRequestApi {
    private int articleId;


    public DeleteShareArticleApi setArticleId(int articleId){
        this.articleId = articleId;
        return this;

    }


    @Override
    public String getApi() {
        return "lg/user_article/delete/"+articleId+"/json";
    }
}
