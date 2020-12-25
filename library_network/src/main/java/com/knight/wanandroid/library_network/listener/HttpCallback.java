package com.knight.wanandroid.library_network.listener;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanadroid
 * @Date 2020/12/25 18:04
 * @descript:
 */
public class HttpCallback<T> implements OnHttpListener<T> {

    private final OnHttpListener mListener;

    public HttpCallback(OnHttpListener listener) {
        mListener = listener;
    }

    @Override
    public void onStart(Call call) {
        if (mListener != null) {
            mListener.onStart(call);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onSucceed(T result) {
        if (mListener != null) {
            mListener.onSucceed(result);
        }
    }

    @Override
    public void onFail(Exception e) {
        if (mListener != null) {
            mListener.onFail(e);
        }
    }

    @Override
    public void onEnd(Call call) {
        if (mListener != null) {
            mListener.onEnd(call);
        }
    }
}
