package com.knight.wanandroid.library_network.callback;

import android.text.TextUtils;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.data.CallProxy;
import com.knight.wanandroid.library_network.exception.MD5Exception;
import com.knight.wanandroid.library_network.exception.NullBodyException;
import com.knight.wanandroid.library_network.lifecycle.HttpLifecycleControl;
import com.knight.wanandroid.library_network.listener.OnDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:20
 * @descript:
 */
public final class DownloadCallback extends BaseCallback {

    /** 文件 MD5 正则表达式 */
    private static final String FILE_MD5_REGEX = "^[\\w]{32}$";

    /** 保存的文件 */
    private File mFile;

    /** 校验的 MD5 */
    private String mMd5;

    /** 下载监听回调 */
    private OnDownloadListener mListener;

    /** 下载总字节 */
    private long mTotalByte;

    /** 已下载字节 */
    private long mDownloadByte;

    /** 下载进度 */
    private int mDownloadProgress;

    public DownloadCallback(LifecycleOwner lifecycleOwner, CallProxy call, File file, String md5, OnDownloadListener listener) {
        super(lifecycleOwner, call);
        mFile = file;
        mMd5 = md5;
        mListener = listener;

        NetWorkUtils.post(() -> {
            if (mListener != null && HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                mListener.onStart(mFile);
            }
        });
    }

    @Override
    protected void onResponse(Response response) throws Exception {
        // 如果没有指定文件的 md5 值
        if (mMd5 == null) {
            // 获取响应头中的文件 MD5 值
            String md5 = response.header("Content-MD5");
            // 这个 md5 值必须是文件的 md5 值
            if (!TextUtils.isEmpty(md5) && md5.matches(FILE_MD5_REGEX)) {
                mMd5 = md5;
            }
        }

        File parentFile = mFile.getParentFile();
        if (parentFile != null) {
            NetWorkUtils.createFolder(parentFile);
        }
        ResponseBody body = response.body();
        if (body == null) {
            throw new NullBodyException("The response body is empty");
        }

        mTotalByte = body.contentLength();
        if (mTotalByte < 0) {
            mTotalByte = 0;
        }
        // 如果这个文件已经下载过，并且经过校验 MD5 是同一个文件的话，就直接回调下载成功监听
        if (!TextUtils.isEmpty(mMd5) && mFile.isFile() &&
                mMd5.equalsIgnoreCase(NetWorkUtils.getFileMd5(mFile))) {
            NetWorkUtils.post(() -> {
                if (mListener != null && HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                    mListener.onComplete(mFile);
                    mListener.onEnd(mFile);
                }
            });
            return;
        }

        int readLength;
        mDownloadByte = 0;
        byte[] bytes = new byte[8192];
        InputStream inputStream = body.byteStream();
        FileOutputStream outputStream = new FileOutputStream(mFile);
        while ((readLength = inputStream.read(bytes)) != -1) {
            mDownloadByte += readLength;
            outputStream.write(bytes, 0, readLength);
            NetWorkUtils.post(() -> {
                if (mListener != null && HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                    mListener.onByte(mFile, mTotalByte, mDownloadByte);
                    int progress = NetWorkUtils.getProgressProgress(mTotalByte, mDownloadByte);
                    // 只有下载进度发生改变的时候才回调此方法，避免引起不必要的 View 重绘
                    if (progress != mDownloadProgress) {
                        mDownloadProgress = progress;
                        mListener.onProgress(mFile, mDownloadProgress);
                    }
                    EasyLog.print(mFile.getPath() + " 正在下载，总字节：" + mTotalByte + "，已下载：" + mDownloadByte +
                            "，进度：" + progress + " %");
                }
            });
        }
        NetWorkUtils.closeStream(inputStream);
        NetWorkUtils.closeStream(outputStream);

        String md5 = NetWorkUtils.getFileMd5(mFile);
        if (!TextUtils.isEmpty(mMd5) && !mMd5.equalsIgnoreCase(md5)) {
            // 文件 MD5 值校验失败
            throw new MD5Exception("MD5 verify failure", md5);
        }

        NetWorkUtils.post(() -> {
            if (mListener != null && HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                mListener.onComplete(mFile);
                mListener.onEnd(mFile);
            }
        });
    }

    @Override
    protected void onFailure(final Exception e) {
        // 打印错误堆栈
        EasyLog.print(e);
        NetWorkUtils.post(() -> {
            if (mListener != null && HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                mListener.onError(mFile, e);
                mListener.onEnd(mFile);
            }
        });
    }
}
