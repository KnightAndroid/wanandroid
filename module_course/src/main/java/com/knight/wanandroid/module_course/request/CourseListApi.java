package com.knight.wanandroid.module_course.request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * Author:Knight
 * Time:2022/5/13 17:26
 * Description:CourseListApi
 */
public class CourseListApi implements IRequestApi {
    @Override
    public String getApi() {
        return "chapter/547/sublist/json";
    }
}
