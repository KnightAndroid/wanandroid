package com.knight.wanandroid.library_base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_common.utils.NetWorkUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/6 10:59
 * @descript:
 */
public class NetWorkChangeReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetWorkUtils.isNetWorkConnected(context);
        if (isConnected) {
            EventBus.getDefault().post(new EventBusUtils.NetWorkState(true));
        } else {
            EventBus.getDefault().post(new EventBusUtils.NetWorkState(false));
        }
    }
}
