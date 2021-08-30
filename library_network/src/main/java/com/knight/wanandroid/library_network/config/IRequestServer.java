package com.knight.wanandroid.library_network.config;

import com.knight.wanandroid.library_network.data.BodyType;
import com.knight.wanandroid.library_network.data.CacheMode;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:30
 * @descript:请求服务配置
 */
public interface IRequestServer extends
        IRequestHost, IRequestPath, IRequestClient,IRequestType,IRequestCache {

    @Override
    default BodyType getType() {
        // 默认以Json的方式提交
        return BodyType.FORM;
    }
    @Override
    default CacheMode getMode() {
        // 默认的缓存方式
        return CacheMode.DEFAULT;
    }
    
    @Override
    default String getPath() {
        // 服务器路径可填可不填
        return "";
    }
}