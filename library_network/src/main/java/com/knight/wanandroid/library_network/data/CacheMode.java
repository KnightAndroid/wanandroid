package com.knight.wanandroid.library_network.data;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/30 14:30
 * @descript:
 */
public enum CacheMode {

    /**
     * 默认（按照 Http 协议来缓存）
     */
    DEFAULT,

    /**
     * 不使用缓存（禁用 Http 协议缓存）
     */
    NO_CACHE,

    /**
     * 只使用缓存
     *
     * 有缓存的情况下：读取缓存 -> 回调成功
     * 无缓存的情况下：请求网络 -> 写入缓存 -> 回调成功
     */
    USE_CACHE_ONLY,

    /**
     * 优先使用缓存
     *
     * 有缓存的情况下：先读缓存 —> 回调成功 —> 请求网络 —> 刷新缓存
     * 无缓存的情况下：请求网络 -> 写入缓存 -> 回调成功
     */
    USE_CACHE_FIRST,

    /**
     * 只在网络请求失败才去读缓存
     */
    USE_CACHE_AFTER_FAILURE
}
