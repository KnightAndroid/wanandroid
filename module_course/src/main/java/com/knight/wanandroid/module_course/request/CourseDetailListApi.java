package com.knight.wanandroid.module_course.request;

import com.knight.wanandroid.library_base.constants.BaseConstants;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * Author:Knight
 * Time:2022/5/16 11:15
 * Description:CourseDetailListApi
 */
public class CourseDetailListApi implements IRequestApi {

    private int page;
    private int cid;

    public CourseDetailListApi setPage(int page){
        this.page = page;
        return this;

    }

    public CourseDetailListApi setCid(int cid){
        this.cid = cid;
        return this;

    }
    @Override
    public String getApi() {
        return "article/list/"+page+"/json?cid="+cid+"&order_type=1"+"&page_size="+ BaseConstants.PAGR_SIZE;
    }
}
