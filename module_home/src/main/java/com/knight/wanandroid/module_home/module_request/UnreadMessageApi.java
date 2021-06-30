package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/30 17:56
 * @descript:未读消息数量api
 */
public class UnreadMessageApi implements IRequestApi {
    @Override
    public String getApi() {
        return "message/lg/count_unread/json";
    }
}
