package com.knight.wanandroid.module_mine.request;

import com.knight.wanandroid.library_base.constants.BaseConstants;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/8 16:51
 * @descript:
 */
public final class MyShareArticleApi implements IRequestApi {


    private int page;


    public MyShareArticleApi setPage(int page){
        this.page = page;
        return this;

    }

    @Override
    public String getApi() {
        return "user/lg/private_articles/" + page + "/json?page_size="+ BaseConstants.PAGR_SIZE;
    }
}
