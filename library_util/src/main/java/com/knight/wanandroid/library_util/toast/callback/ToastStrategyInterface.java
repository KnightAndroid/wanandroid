package com.knight.wanandroid.library_util.toast.callback;

import android.app.Application;

/**
 * @author created by luguian
 * @organize 车童网
 * @Date 2021/9/8 15:09
 * @descript:
 */
public interface ToastStrategyInterface {
    /**
     * 注册策略
     */
    void registerStrategy(Application application);

    /**
     * 绑定样式
     */
    void bindStyle(ToastStyleInterface<?> style);

    /**
     * 创建 Toast
     */
    ToastInterface createToast(Application application);

    /**
     * 显示 Toast
     */
    void showToast(CharSequence text);

    /**
     * 取消 Toast
     */
    void cancelToast();
}
