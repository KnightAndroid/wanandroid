package com.knight.wanandroid.library_base.listener;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/18 18:26
 * @descript:
 */
public interface NetworkStatusListener {
    /**
     * 已经连接
     */
    void onConnect();

    /**
     *
     * 断开连接
     */
    void disConnect();
}
