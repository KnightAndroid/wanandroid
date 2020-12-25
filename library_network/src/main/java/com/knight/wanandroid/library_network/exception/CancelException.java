package com.knight.wanandroid.library_network.exception;



/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:43
 * @descript:请求取消异常
 */
public final class CancelException extends HttpException {

    public CancelException(String message) {
        super(message);
    }

    public CancelException(String message, Throwable cause) {
        super(message, cause);
    }
}
