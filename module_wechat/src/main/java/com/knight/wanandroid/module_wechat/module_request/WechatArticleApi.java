package com.knight.wanandroid.module_wechat.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/14 14:52
 * @descript:
 */
public final class WechatArticleApi implements IRequestApi {


    private int page;
    private int cid;


    public WechatArticleApi setPage(int page) {
        this.page = page;
        return this;
    }

    public WechatArticleApi setCid(int cid){
        this.cid = cid;
        return this;
    }



    @Override
    public String getApi() {
        return "wxarticle/list/"+cid+"/"+ page+"/json";
    }
}
