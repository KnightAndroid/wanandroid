package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.annotation.HttpRename;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 17:55
 * @descript:
 */
public final class SearchKeywordsApi implements IRequestApi {

    private int page;
    @HttpRename("k")
    private String keyWord;
    public int getPage() {
        return page;
    }

    public SearchKeywordsApi setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public String getApi() {
        return "article/query/"+page+"/json";
    }

    /**
     *
     * 设置请求参数
     * @param keyWord
     * @return
     */
    public SearchKeywordsApi setKeyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }



}
