package com.knight.wanandroid.module_wechat.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/10 14:58
 * @descript:
 */
public final class SearchWechatArticlesApi implements IRequestApi {

    private int cid;
    private int page;
    private String keyword;


    public SearchWechatArticlesApi setCid (int cid) {
        this.cid = cid;
        return this;
    }

    public SearchWechatArticlesApi setPage(int page){
        this.page = page;
        return this;
    }

    public SearchWechatArticlesApi setWearchKeyword(String keyword){
        this.keyword = keyword;
        return this;

    }


    @Override
    public String getApi() {
        return "wxarticle/list/"+cid+"/"+page+"/json?k="+keyword;
    }
}
