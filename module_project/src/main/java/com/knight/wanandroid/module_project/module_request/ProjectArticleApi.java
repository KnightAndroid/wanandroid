package com.knight.wanandroid.module_project.module_request;

import com.knight.wanandroid.library_base.constants.BaseConstants;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/27 17:51
 * @descript:项目文章
 */
public final class ProjectArticleApi implements IRequestApi {
    private int page;
    private int cid;


    public ProjectArticleApi setPage(int page){
        this.page = page;
        return this;
    }

    public ProjectArticleApi setCid(int cid){
        this.cid = cid;
        return this;
    }

    @Override
    public String getApi() {
        return "project/list/"+page+"/json?cid="+cid+"&page_size="+ BaseConstants.PAGR_SIZE;
    }
}
