package com.knight.wanandroid.library_network.server;

import com.knight.wanandroid.library_network.config.IRequestServer;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/7 14:57
 * @descript:生产环境 地址
 */
public class ReleaseServer implements IRequestServer {
    @Override
    public String getHost() {
        return "https://www.wanandroid.com/";
    }
}
