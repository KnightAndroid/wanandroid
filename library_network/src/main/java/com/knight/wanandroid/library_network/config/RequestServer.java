package com.knight.wanandroid.library_network.config;

import com.knight.wanandroid.library_network.annotation.HttpIgnore;

/**
 * @author created by luguian
 * @organize 车童网
 * @Date 2020/12/25 17:34
 * @descript:服务器简单配置
 */
public final class RequestServer implements IRequestServer {

    /**
     * 主机地址
     */
    @HttpIgnore
    private String mHost;

    /**
     * 接口路径
     */
    @HttpIgnore
    private String mPath;

    public RequestServer(String host) {
        this(host, "");
    }

    public RequestServer(String host, String path) {
        mHost = host;
        mPath = path;
    }

    @Override
    public String getHost() {
        return mHost;
    }

    @Override
    public String getPath() {
        return mPath;
    }

    @Override
    public String toString() {
        return mHost + mPath;
    }
}