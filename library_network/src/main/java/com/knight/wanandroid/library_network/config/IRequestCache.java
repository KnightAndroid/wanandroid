package com.knight.wanandroid.library_network.config;

import com.knight.wanandroid.library_network.data.CacheMode;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/30 15:05
 * @descript:
 */
public interface IRequestCache {

    /**
     * 接口缓存方式
     */
    CacheMode getMode();
}