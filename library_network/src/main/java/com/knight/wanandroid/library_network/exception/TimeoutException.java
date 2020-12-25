package com.knight.wanandroid.library_network.exception;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:55
 * @descript:服务器超时异常
 */
public final class TimeoutException extends HttpException {

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}