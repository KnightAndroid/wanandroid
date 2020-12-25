package com.knight.wanandroid.library_network.exception;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:53
 * @descript:返回结果异常
 */
public final class ResultException extends HttpException {

    private final Object mData;

    public ResultException(String message, Object data) {
        super(message);
        mData = data;
    }

    public ResultException(String message, Throwable cause, Object data) {
        super(message, cause);
        mData = data;
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T getData() {
        return (T) mData;
    }
}