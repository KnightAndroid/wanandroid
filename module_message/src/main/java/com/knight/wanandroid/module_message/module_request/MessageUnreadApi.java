package com.knight.wanandroid.module_message.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/1 11:25
 * @descript:未读消息api
 */
public class MessageUnreadApi implements IRequestApi {

    private int page;


    public MessageUnreadApi setUnreadMessagePage(int page){
        this.page = page;
        return this;

    }

    @Override
    public String getApi() {
        return "message/lg/unread_list/"+page+"/json";
    }
}
