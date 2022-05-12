package com.knight.wanandroid.module_utils.request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * Author:Knight
 * Time:2022/5/12 14:56
 * Description:UtilsApi
 */
public class UtilsApi implements IRequestApi {
    @Override
    public String getApi() {
        return "tools/list/json";
    }
}
