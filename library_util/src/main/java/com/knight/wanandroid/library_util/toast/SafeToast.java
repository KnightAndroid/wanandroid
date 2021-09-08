package com.knight.wanandroid.library_util.toast;

import android.app.Application;
import android.os.Handler;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/8 16:06
 * @descript:
 */
public final class SafeToast extends SystemToast {

    public SafeToast(Application application) {
        super(application);

        // 反射 Toast 中的字段
        try {
            // 获取 mTN 字段对象
            Field mTNField = Toast.class.getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object mTN = mTNField.get(this);

            // 获取 mTN 中的 mHandler 字段对象
            Field mHandlerField = mTNField.getType().getDeclaredField("mHandler");
            mHandlerField.setAccessible(true);
            Handler mHandler = (Handler) mHandlerField.get(mTN);

            // 偷梁换柱
            mHandlerField.set(mTN, new SafeHandler(mHandler));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            // Android 9.0 上反射会出现报错
            // Accessing hidden field Landroid/widget/Toast;->mTN:Landroid/widget/Toast$TN;
            // java.lang.NoSuchFieldException: No field mTN in class Landroid/widget/Toast;
            e.printStackTrace();
        }
    }
}
