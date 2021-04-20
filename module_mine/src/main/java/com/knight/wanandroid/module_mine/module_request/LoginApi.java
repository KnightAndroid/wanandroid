package com.knight.wanandroid.module_mine.module_request;

import com.knight.wanandroid.library_network.annotation.HttpRename;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/19 18:20
 * @descript:
 */
public class LoginApi implements IRequestApi {


    @HttpRename("username")
    private String username;

    @HttpRename("password")
    private String password;



    @Override
    public String getApi() {
        return "user/login";
    }

    public LoginApi setUserName(String username){
        this.username = username;
        return this;

    }


    public LoginApi setPassword(String password){
        this.password = password;
        return this;

    }
}
