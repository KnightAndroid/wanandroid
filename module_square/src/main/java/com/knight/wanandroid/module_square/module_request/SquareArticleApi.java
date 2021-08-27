package com.knight.wanandroid.module_square.module_request;

import com.knight.wanandroid.library_base.constants.BaseConstants;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/25 19:55
 * @descript:
 */
public final class SquareArticleApi implements IRequestApi {
    private int page;
    public int getPage() {
        return page;
    }


    public SquareArticleApi setPage(int page){
        this.page = page;
        return this;

    }
    @Override
    public String getApi() {
        return "user_article/list/"+page+"/json?page_size="+ BaseConstants.PAGR_SIZE;
    }
}
