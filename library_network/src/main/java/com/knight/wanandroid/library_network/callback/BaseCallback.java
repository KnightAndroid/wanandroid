package com.knight.wanandroid.library_network.callback;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.HttpConfig;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.data.CallProxy;
import com.knight.wanandroid.library_network.lifecycle.HttpLifecycleControl;

import java.io.IOException;
import java.net.SocketTimeoutException;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:17
 * @descript:接口回调基类
 */
public abstract class BaseCallback implements Callback {

    /** 请求任务对象 */
    private CallProxy mCall;

    /** 当前重试次数 */
    private int mRetryCount;

    /** 生命周期管理 */
    private LifecycleOwner mLifecycleOwner;

    BaseCallback(LifecycleOwner lifecycleOwner, CallProxy call) {
        mCall = call;
        mLifecycleOwner = lifecycleOwner;
        HttpLifecycleControl.bind(lifecycleOwner);
    }

    CallProxy getCall() {
        return mCall;
    }

    LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }

    @Override
    public void onResponse(Call call, Response response) {
        try {
            // 收到响应
            onResponse(response);
        } catch (Exception e) {
            // 回调失败
            onFailure(e);
        } finally {
            // 关闭响应
            response.close();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        // 服务器请求超时重试
        if (e instanceof SocketTimeoutException && mRetryCount < HttpConfig.getInstance().getRetryCount()) {
            // 设置延迟 N 秒后重试该请求
            NetWorkUtils.postDelayed(() -> {
                // 前提是宿主还没有被销毁
                if (HttpLifecycleControl.isLifecycleActive(mLifecycleOwner)) {
                    mRetryCount++;
                    Call newCall = call.clone();
                    mCall.setCall(newCall);
                    newCall.enqueue(BaseCallback.this);
                    EasyLog.print("请求超时，正在延迟重试，重试次数：" + mRetryCount + "/" + HttpConfig.getInstance().getRetryCount());
                } else {
                    EasyLog.print("宿主已被销毁，无法对请求进行重试");
                }
            }, HttpConfig.getInstance().getRetryTime());
            return;
        }
        onFailure(e);
    }

    protected abstract void onResponse(Response response) throws Exception;

    protected abstract void onFailure(Exception e);
}
