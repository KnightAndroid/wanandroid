package com.knight.wanandroid.library_network.config;

import com.knight.wanandroid.library_network.data.BodyType;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:31
 * @descript: 请求接口配置
 */
public interface IRequestType {

    /**
     * 参数提交类型
     */
    BodyType getType();
}
