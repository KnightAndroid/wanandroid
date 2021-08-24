package com.knight.wanandroid.module_mine.module_request;

import com.knight.wanandroid.library_network.annotation.HttpRename;
import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/22 16:45
 * @descript:注册接口
 */
public final class RegisterApi implements IRequestApi {

    @HttpRename("username")
    private String username;

    @HttpRename("password")
    private String password;

    @HttpRename("repassword")
    private String repassword;


    @Override
    public String getApi() {
        return "user/register";
    }


    public RegisterApi setUserName(String username){
        this.username = username;
        return this;
    }

    public RegisterApi setPassword(String password){
        this.password = password;
        return this;
    }

    public RegisterApi setRepassword(String repassword){
        this.repassword = repassword;
        return this;

    }
}
