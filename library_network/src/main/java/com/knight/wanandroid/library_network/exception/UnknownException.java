package com.knight.wanandroid.library_network.exception;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:58
 * @descript:未知异常
 */
public final class UnknownException extends HttpException {

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
