package com.knight.wanandroid.library_util.toast;

import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/8 16:03
 * @descript:
 */
public final class SafeHandler extends Handler {
    private final Handler mHandler;

    SafeHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void handleMessage(final Message msg) {
        // 捕获这个异常，避免程序崩溃
        try {
            // 目前发现在 Android 7.1 主线程被阻塞之后弹吐司会导致崩溃，可使用 Thread.sleep(5000) 进行复现
            // 查看源码得知 Google 已经在 Android 8.0 已经修复了此问题
            // 主线程阻塞之后 Toast 也会被阻塞，Toast 因为显示超时导致 Window Token 失效
            mHandler.handleMessage(msg);
        } catch (WindowManager.BadTokenException | IllegalStateException e) {
            // android.view.WindowManager$BadTokenException：Unable to add window -- token android.os.BinderProxy is not valid; is your activity running?
            // java.lang.IllegalStateException：View android.widget.TextView has already been added to the window manager.
            e.printStackTrace();
        }
    }
}
