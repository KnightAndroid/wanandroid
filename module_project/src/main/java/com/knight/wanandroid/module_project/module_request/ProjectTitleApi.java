package com.knight.wanandroid.module_project.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by 项目列表
 * @organize wanandroid
 * @Date 2021/4/27 15:17
 * @descript:
 */
public class ProjectTitleApi implements IRequestApi {


    @Override
    public String getApi() {
        return "project/tree/json";
    }
}
