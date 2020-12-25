package com.knight.wanandroid.library_network.exception;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:55
 * @descript:服务器连接异常
 */
public final class ServerException extends HttpException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
