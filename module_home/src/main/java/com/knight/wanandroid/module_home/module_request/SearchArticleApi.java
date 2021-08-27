package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_base.constants.BaseConstants;
import com.knight.wanandroid.library_network.annotation.HttpRename;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 11:06
 * @descript:搜索Api
 */
public final class SearchArticleApi implements IRequestApi {

    private int page;


    @HttpRename("k")
    private String keyWord;

    public int getPage() {
        return page;
    }

    public SearchArticleApi setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public String getApi() {
        return "article/query/"+page+"/json?page_size="+ BaseConstants.PAGR_SIZE;
    }

    /**
     *
     * 设置请求参数
     * @param keyWord
     * @return
     */
    public SearchArticleApi setKeyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }




}
