package com.knight.wanandroid.library_network.callback;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.HttpConfig;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.data.CacheMode;
import com.knight.wanandroid.library_network.lifecycle.HttpLifecycleControl;
import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_network.request.BaseRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:22
 * @descript:正常接口回调
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class NormalCallback extends BaseCallback {

    /** 请求配置 */
    private final BaseRequest mBaseRequest;
    /** 接口回调 */
    private OnHttpListener mListener;
    public NormalCallback(BaseRequest request) {
        super(request);
        mBaseRequest = request;
    }

    public NormalCallback setListener(OnHttpListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void start() {
        CacheMode cacheMode = mBaseRequest.getRequestCache().getMode();
        if (cacheMode != CacheMode.USE_CACHE_ONLY && cacheMode != CacheMode.USE_CACHE_FIRST) {
            super.start();
            return;
        }

        try {
            Object result = mBaseRequest.getRequestHandler().readCache(mBaseRequest.getLifecycleOwner(),
                    mBaseRequest.getRequestApi(), NetWorkUtils.getReflectType(mListener));
            EasyLog.print("ReadCache result：" + result);

            // 如果没有缓存，就请求网络
            if (result == null) {
                super.start();
                return;
            }

            // 读取缓存成功
            NetWorkUtils.post(() -> {
                if (mListener == null || !HttpLifecycleControl.isLifecycleActive(mBaseRequest.getLifecycleOwner())) {
                    return;
                }
                mListener.onStart(getCall());
                mListener.onSucceed(result, true);
                mListener.onEnd(getCall());
            });

            // 如果当前模式是先读缓存再写请求
            if (cacheMode == CacheMode.USE_CACHE_FIRST) {
                NetWorkUtils.postDelayed(() -> {
                    if (!HttpLifecycleControl.isLifecycleActive(mBaseRequest.getLifecycleOwner())) {
                        return;
                    }
                    // 将回调置为空，避免出现两次回调
                    mListener = null;
                    super.start();
                }, 1);
            }

        } catch (Throwable throwable) {
            EasyLog.print("ReadCache error");
            EasyLog.print(throwable);
            super.start();
        }
    }

    @Override
    protected void onStart(Call call) {
        NetWorkUtils.post(() -> {
            if (mListener == null || !HttpLifecycleControl.isLifecycleActive(mBaseRequest.getLifecycleOwner())) {
                return;
            }
            mListener.onStart(call);
        });
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void onResponse(Response response) throws Exception {
        EasyLog.print("RequestTime：" + (response.receivedResponseAtMillis() - response.sentRequestAtMillis()) + " ms");

        //解析 Bean 类对象
        final Object result = HttpConfig.getInstance().getHandler().requestSucceed(mBaseRequest.getLifecycleOwner(), mBaseRequest.getRequestApi(),response, NetWorkUtils.getReflectType(mListener));
        CacheMode cacheMode = mBaseRequest.getRequestCache().getMode();
        if (cacheMode == CacheMode.USE_CACHE_ONLY || cacheMode == CacheMode.USE_CACHE_FIRST) {
            try {
                boolean writeSucceed = mBaseRequest.getRequestHandler().writeCache(mBaseRequest.getLifecycleOwner(),
                        mBaseRequest.getRequestApi(), response, result);
                EasyLog.print("WriteCache result：" + writeSucceed);
            } catch (Throwable throwable) {
                EasyLog.print("WriteCache error");
                EasyLog.print(throwable);
            }
        }

        NetWorkUtils.post( () -> {
            if (mListener == null && !HttpLifecycleControl.isLifecycleActive(mBaseRequest.getLifecycleOwner())) {
                return;
            }
            mListener.onSucceed(result);
            mListener.onEnd(getCall());
        });
    }

    @Override
    protected void onFailure(Exception e) {
        // 如果设置了只在网络请求失败才去读缓存
        if (e instanceof IOException && mBaseRequest.getRequestCache().getMode() == CacheMode.USE_CACHE_AFTER_FAILURE) {
            try {
                Object result = mBaseRequest.getRequestHandler().readCache(mBaseRequest.getLifecycleOwner(),
                        mBaseRequest.getRequestApi(), NetWorkUtils.getReflectType(mListener));
                EasyLog.print("ReadCache result：" + result);
                if (result != null) {
                    NetWorkUtils.post(() -> {
                        if (mListener == null && !HttpLifecycleControl.isLifecycleActive(mBaseRequest.getLifecycleOwner())) {
                           return;
                        }
                        mListener.onSucceed(result, true);
                        mListener.onEnd(getCall());

                    });
                    return;
                }
            } catch (Throwable throwable) {
                EasyLog.print("ReadCache error");
                EasyLog.print(throwable);
            }
        }


        final Exception exception = HttpConfig.getInstance().getHandler().requestFail(mBaseRequest.getLifecycleOwner(),mBaseRequest.getRequestApi(), e);
        //打印错误堆栈
        EasyLog.print(exception);
        NetWorkUtils.post(() -> {
            if (mListener == null && !HttpLifecycleControl.isLifecycleActive(mBaseRequest.getLifecycleOwner())) {
                return;
            }
            mListener.onFail(exception);
            mListener.onEnd(getCall());
        });
    }
}