package com.knight.wanandroid.library_network.callback;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.HttpConfig;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.data.CallProxy;
import com.knight.wanandroid.library_network.lifecycle.HttpLifecycleControl;
import com.knight.wanandroid.library_network.listener.OnHttpListener;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.Response;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:22
 * @descript:正常接口回调
 */
public final class NormalCallback extends BaseCallback {

    private LifecycleOwner mLifecycle;
    private OnHttpListener mListener;


    public NormalCallback(LifecycleOwner lifecycleOwner, CallProxy call, OnHttpListener listener) {
        super(lifecycleOwner, call);
        mLifecycle = lifecycleOwner;
        mListener = listener;

        NetWorkUtils.post(() -> {
            if (mListener != null && HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                mListener.onStart(call);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onResponse(Response response) throws Exception {
        EasyLog.print("RequestTime：" + (response.receivedResponseAtMillis() - response.sentRequestAtMillis()) + " ms");
        final Object result = HttpConfig.getInstance().getHandler().requestSucceed(mLifecycle, response, NetWorkUtils.getReflectType(mListener));
        NetWorkUtils.post( () -> {
            if (mListener != null && HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                mListener.onSucceed(result);
                mListener.onEnd(getCall());
            }
        });
    }

    @Override
    protected void onFailure(Exception e) {
        EasyLog.print(e);
        final Exception exception = HttpConfig.getInstance().getHandler().requestFail(mLifecycle, e);
        NetWorkUtils.post(() -> {
            if (mListener != null && HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                mListener.onFail(exception);
                mListener.onEnd(getCall());
            }
        });
    }
}