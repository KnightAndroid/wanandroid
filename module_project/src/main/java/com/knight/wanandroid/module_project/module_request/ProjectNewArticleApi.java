package com.knight.wanandroid.module_project.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/28 10:57
 * @descript:最新项目api
 */
public class ProjectNewArticleApi implements IRequestApi {
    private int page;


    public ProjectNewArticleApi setPage(int page){
        this.page = page;
        return this;

    }
    @Override
    public String getApi() {
        return "article/listproject/"+page+"/json";
    }
}
