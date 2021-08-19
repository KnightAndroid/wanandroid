package com.knight.wanandroid.library_base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.knight.wanandroid.library_base.listener.NetworkStatusListener;
import com.knight.wanandroid.library_common.utils.NetWorkUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/6 10:59
 * @descript:
 */
public class NetWorkChangeReceiver extends BroadcastReceiver {


    private NetworkStatusListener mNetworkStatusListener;

    public void setNetworkStatusListener (NetworkStatusListener mNetworkStatusListener) {
        this.mNetworkStatusListener = mNetworkStatusListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetWorkUtils.isNetWorkConnected(context);
        if (isConnected) {
            if (mNetworkStatusListener != null) {
                mNetworkStatusListener.onConnect();
            }
        } else {
            if (mNetworkStatusListener != null) {
                mNetworkStatusListener.disConnect();
            }
        }
    }

}
