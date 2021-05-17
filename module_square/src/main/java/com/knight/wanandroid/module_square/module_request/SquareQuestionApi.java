package com.knight.wanandroid.module_square.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by luguian
 * @organize 车童网
 * @Date 2021/5/17 14:56
 * @descript:
 */
public class SquareQuestionApi implements IRequestApi {
    private int page;
    public int getPage() {
        return page;
    }


    public SquareQuestionApi setPage(int page){
        this.page = page;
        return this;

    }
    @Override
    public String getApi() {
        return "wenda/list/"+page+"/json";
    }
}
