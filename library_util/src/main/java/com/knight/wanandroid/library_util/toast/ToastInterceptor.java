package com.knight.wanandroid.library_util.toast;

import com.knight.wanandroid.library_util.BuildConfig;
import com.knight.wanandroid.library_util.LogUtils;
import com.knight.wanandroid.library_util.toast.callback.ToastInterceptorInterface;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/8 16:27
 * @descript:
 */
public final class ToastInterceptor implements ToastInterceptorInterface {
    @Override
    public boolean intercept(CharSequence text) {
        if (BuildConfig.DEBUG) {
            // 获取调用的堆栈信息
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            // 跳过最前面两个堆栈
            for (int i = 2; stackTrace.length > 2 && i < stackTrace.length; i++) {
                // 获取代码行数
                int lineNumber = stackTrace[i].getLineNumber();
                // 获取类的全路径
                String className = stackTrace[i].getClassName();
                if (lineNumber <= 0 || className.startsWith(ToastUtils.class.getName())) {
                    continue;
                }

                LogUtils.d("Toast" + stackTrace[i].getFileName() + ":" + lineNumber + ") " + text.toString());
                break;
            }
        }
        return false;
    }
}
