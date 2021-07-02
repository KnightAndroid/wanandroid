package com.knight.wanandroid.library_widget.flowlayout.listener;

import android.view.MotionEvent;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/2 15:16
 * @descript:事件拦截
 *
 */
public interface OnInterceptTouchEventListener {
    boolean onInterceptTouchEvent(MotionEvent ev);
}
