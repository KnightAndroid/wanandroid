package com.knight.wanandroid.library_network.data;

import okhttp3.MediaType;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/30 14:31
 * @descript:
 */
public final class ContentType {
    /** 字节流 */
    public static final MediaType STREAM = MediaType.parse("application/octet-stream");

    /** Json */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /** 纯文本 */
    public static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
}
