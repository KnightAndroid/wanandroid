package com.knight.wanandroid.library_componentbase.emptyservice;

import com.knight.wanandroid.library_componentbase.service.UserService;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/1/7 20:13
 * @descript:用户信息空实现
 */
public class EmptyUserServiceImpl implements UserService {

    @Override
    public String getUserName(){
        return "";
    }


    @Override
    public int getUserId(){
        return 0;
    }








}
