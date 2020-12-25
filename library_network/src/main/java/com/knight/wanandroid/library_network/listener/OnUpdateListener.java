package com.knight.wanandroid.library_network.listener;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:07
 * @descript: 带上传进度回调的监听器
 */
public interface OnUpdateListener<T> extends OnHttpListener<T> {

    /**
     * 上传字节改变
     *
     * @param totalByte             总字节数
     * @param updateByte            已上传字节数
     */
    default void onByte(long totalByte, long updateByte) {}

    /**
     * 上传进度改变
     *
     * @param progress          上传进度值（0-100）
     */
    void onProgress(int progress);
}