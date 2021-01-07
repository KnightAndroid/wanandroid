package com.knight.wanandroid.library_componentbase;

import com.knight.wanandroid.library_componentbase.emptyservice.EmptyUserServiceImpl;
import com.knight.wanandroid.library_componentbase.service.UserService;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/1/7 20:31
 * @descript: 服务工厂
 */
public class ServiceFactory {


    //用户服务模块
    private UserService mUserService;


    /**
     *
     * 禁止外部创建SerFactory 对象
     */
    private ServiceFactory(){}




    public static ServiceFactory getInstance(){
           return Inner.serviceFactory;
    }


    private static class Inner{
        private static ServiceFactory serviceFactory = new ServiceFactory();
    }

    /**
     *
     * 赋值Service实例
     * @param mUserService
     */
    public void setUserService(UserService mUserService){
        this.mUserService = mUserService;

    }


    public UserService getUserService(){
        if(mUserService == null){
            mUserService = new EmptyUserServiceImpl();
        }

        return mUserService;

    }

}
