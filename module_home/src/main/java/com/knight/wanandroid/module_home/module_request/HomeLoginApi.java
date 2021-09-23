package com.knight.wanandroid.module_home.module_request;

import com.knight.wanandroid.library_network.annotation.HttpRename;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/23 17:06
 * @descript:
 */
public final class HomeLoginApi implements IRequestApi {
    @HttpRename("username")
    private String username;

    @HttpRename("password")
    private String password;





    @Override
    public String getApi() {
        return "user/login";
    }

    public HomeLoginApi setUserName(String username){
        this.username = username;
        return this;

    }


    public HomeLoginApi setPassword(String password){
        this.password = password;
        return this;

    }
}
