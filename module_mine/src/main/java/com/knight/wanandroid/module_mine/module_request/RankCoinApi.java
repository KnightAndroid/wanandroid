package com.knight.wanandroid.module_mine.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 17:13
 * @descript:
 */
public final class RankCoinApi implements IRequestApi {

    private int page;

    public int getPage() {
        return page;
    }


    public RankCoinApi setPage(int page){
        this.page = page;
        return this;

    }
    @Override
    public String getApi() {
        return "coin/rank/"+page+"/json";
    }
}
