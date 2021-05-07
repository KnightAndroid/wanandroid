package com.knight.wanandroid.module_square.module_request;

import com.knight.wanandroid.library_network.annotation.HttpRename;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/7 16:07
 * @descript:
 */
public class SquareShareArticleApi implements IRequestApi {

    @HttpRename("title")
    private String title;

    @HttpRename("link")
    private String link;

    public SquareShareArticleApi setTitle(String title){
        this.title  = title;
        return this;
    }

    public SquareShareArticleApi setLink(String link){
        this.link = link;
        return this;

    }
    @Override
    public String getApi() {
        return "lg/user_article/add/json";
    }
}
