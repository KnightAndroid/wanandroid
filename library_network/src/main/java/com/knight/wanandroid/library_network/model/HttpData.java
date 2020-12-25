package com.knight.wanandroid.library_network.model;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:37
 * @descript:统一接口数据结构
 */
public class HttpData<T> {

    /** 返回码 */
    private int errorCode;
    /** 提示语 */
    private String errorMsg;
    /** 数据 */
    private T data;

    public int getCode() {
        return errorCode;
    }

    public String getMessage() {
        return errorMsg;
    }

    public T getData() {
        return data;
    }
}
