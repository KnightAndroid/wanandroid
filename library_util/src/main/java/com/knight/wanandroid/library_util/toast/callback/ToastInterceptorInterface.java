package com.knight.wanandroid.library_util.toast.callback;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/8 15:08
 * @descript:
 */
public interface ToastInterceptorInterface {
    /**
     * 根据显示的文本决定是否拦截该 Toast
     */
    boolean intercept(CharSequence text);
}
