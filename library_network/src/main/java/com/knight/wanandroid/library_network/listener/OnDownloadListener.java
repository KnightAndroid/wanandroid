package com.knight.wanandroid.library_network.listener;

import java.io.File;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:05
 * @descript:
 */
public interface OnDownloadListener {

    /**
     * 下载开始
     */
    void onStart(File file);

    /**
     * 下载字节改变
     *
     * @param totalByte             总字节数
     * @param downloadByte          已下载字节数
     */
    default void onByte(File file, long totalByte, long downloadByte) {}

    /**
     * 下载进度改变
     *
     * @param progress              下载进度值（0-100）
     */
    void onProgress(File file, int progress);

    /**
     * 下载完成
     */
    void onComplete(File file);

    /**
     * 下载出错
     */
    void onError(File file, Exception e);

    /**
     * 下载结束
     */
    void onEnd(File file);
}