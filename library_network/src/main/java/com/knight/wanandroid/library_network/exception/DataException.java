package com.knight.wanandroid.library_network.exception;



/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:44
 * @descript:数据解析异常
 */
public final class DataException extends HttpException {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}