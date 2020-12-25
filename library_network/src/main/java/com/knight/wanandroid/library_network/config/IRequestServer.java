package com.knight.wanandroid.library_network.config;

import com.knight.wanandroid.library_network.data.BodyType;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:30
 * @descript:请求服务配置
 */
public interface IRequestServer extends IRequestHost, IRequestPath, IRequestType {

    @Override
    default BodyType getType() {
        // 默认以Json的方式提交
        return BodyType.FORM;
    }

    @Override
    default String getPath() {
        // 服务器路径可填可不填
        return "";
    }
}