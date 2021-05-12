package com.knight.wanandroid.library_base.api;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 20:54
 * @descript:
 */
public class CancelCollectArticleApi implements IRequestApi {

    private int uncollectArticleId;


    public CancelCollectArticleApi setCancelArticleId(int uncollectArticleId) {
        this.uncollectArticleId = uncollectArticleId;
        return this;
    }
    @Override
    public String getApi() {
        return "lg/uncollect_originId/"+uncollectArticleId+"/json";
    }
}
