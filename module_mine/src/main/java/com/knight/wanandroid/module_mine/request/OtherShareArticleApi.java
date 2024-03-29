package com.knight.wanandroid.module_mine.request;

import com.knight.wanandroid.library_base.constants.BaseConstants;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 18:56
 * @descript:
 */
public final class OtherShareArticleApi implements IRequestApi {


    private int uid;

    private int page;

    public OtherShareArticleApi setUid(int uid){
        this.uid = uid;
        return this;
    }


    public OtherShareArticleApi setPage(int page){
        this.page = page;
        return this;
    }


    @Override
    public String getApi() {
        return "user/"+uid+"/share_articles/"+page+"/json?page_size="+ BaseConstants.PAGR_SIZE;
    }
}
