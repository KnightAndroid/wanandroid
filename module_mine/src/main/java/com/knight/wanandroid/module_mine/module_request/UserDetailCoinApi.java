package com.knight.wanandroid.module_mine.module_request;

import com.knight.wanandroid.library_base.constants.BaseConstants;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 11:07
 * @descript:
 */
public final class UserDetailCoinApi implements IRequestApi {

    private int page;

    public int getPage() {
        return page;
    }


    public UserDetailCoinApi setPage(int page){
        this.page = page;
        return this;

    }
    @Override
    public String getApi() {
        return "lg/coin/list/"+page+"/json?page_size="+ BaseConstants.PAGR_SIZE;
    }
}
