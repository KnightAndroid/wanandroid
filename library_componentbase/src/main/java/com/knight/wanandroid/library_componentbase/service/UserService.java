package com.knight.wanandroid.library_componentbase.service;

/**
 * @author created by knight
 * @organize wnandroid
 * @Date 2021/1/7 20:09
 * @descript:定义用户信息模块向外提供的数据接口方法
 */
public interface UserService {

    /**
     * 返回用户名字
     * @return
     *
     */
    String getUserName();

    /**
     * 返回用户id
     * @return
     */
    int getUserId();
    
}
