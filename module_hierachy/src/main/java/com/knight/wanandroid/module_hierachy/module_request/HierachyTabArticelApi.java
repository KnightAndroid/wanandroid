package com.knight.wanandroid.module_hierachy.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/12 17:25
 * @descript:
 */
public final class HierachyTabArticelApi implements IRequestApi {


    private int page;
    private int cid;

    public HierachyTabArticelApi setPage(int page){
        this.page = page;
        return this;
    }

    public HierachyTabArticelApi setCid(int cid){
        this.cid = cid;
        return this;

    }
    @Override
    public String getApi() {
        return "article/list/"+page+"/json?="+cid;
    }
}
