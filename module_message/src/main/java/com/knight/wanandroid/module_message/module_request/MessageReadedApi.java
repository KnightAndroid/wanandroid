package com.knight.wanandroid.module_message.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize waanandroid
 * @Date 2021/6/30 15:05
 * @descript:
 */
public class MessageReadedApi implements IRequestApi {

    private int page;


    public MessageReadedApi setMessageReadedPage(int page){
        this.page = page;
        return this;

    }

    @Override
    public String getApi() {
        return "message/lg/readed_list/"+page+"/json";
    }
}
