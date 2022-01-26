package com.knight.wanandroid.library_util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Author:Knight
 * Time:2022/1/25 16:40
 * Description:WeakRefHandler
 */
public class WeakRefHandler extends Handler {
    private WeakReference<Callback> mWeakReference;

    public WeakRefHandler(Callback callback) {
        mWeakReference = new WeakReference<Callback>(callback);
    }

    public WeakRefHandler(Callback callback, Looper looper) {
        super(looper);
        mWeakReference = new WeakReference<Callback>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mWeakReference != null && mWeakReference.get() != null) {
            Callback callback = mWeakReference.get();
            callback.handleMessage(msg);
        }
    }
}
