package com.knight.wanandroid.library_network.body;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.lifecycle.HttpLifecycleControl;
import com.knight.wanandroid.library_network.listener.OnUpdateListener;

import java.io.IOException;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:13
 * @descript:RequestBody 代理类(用作获取上传进度)
 */
public final class ProgressBody extends RequestBody {

    private final RequestBody mRequestBody;
    private final OnUpdateListener mListener;
    private final LifecycleOwner mLifecycleOwner;

    /** 总字节数 */
    private long mTotalByte;
    /** 已上传字节数 */
    private long mUpdateByte;
    /** 上传进度值 */
    private int mUpdateProgress;

    public ProgressBody(RequestBody body, LifecycleOwner lifecycleOwner, OnUpdateListener listener) {
        mRequestBody = body;
        mLifecycleOwner = lifecycleOwner;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        mTotalByte = contentLength();
        sink = Okio.buffer(new WrapperSink(sink));
        mRequestBody.writeTo(sink);
        sink.flush();
    }


    private class WrapperSink extends ForwardingSink {
        public WrapperSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            mUpdateByte += byteCount;
            NetWorkUtils.post(() -> {
               if (mListener != null && HttpLifecycleControl.isLifecycleActive(mLifecycleOwner)) {
                   mListener.onByte(mTotalByte, mUpdateByte);
               }
                int progress = NetWorkUtils.getProgressProgress(mTotalByte, mUpdateByte);
                // 只有上传进度发生改变的时候才回调此方法，避免引起不必要的 View 重绘
                if (progress != mUpdateProgress) {
                    mUpdateProgress = progress;
                }
                if (mListener != null && HttpLifecycleControl.isLifecycleActive(mLifecycleOwner)) {
                    mListener.onProgress(progress);
                }
                EasyLog.print("正在进行上传" +
                        "，总字节：" + mTotalByte +
                        "，已上传：" + mUpdateByte +
                        "，进度：" + progress + "%");
            });
        }
    }
}