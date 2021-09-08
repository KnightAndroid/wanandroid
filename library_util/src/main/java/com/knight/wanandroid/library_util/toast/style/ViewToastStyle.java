package com.knight.wanandroid.library_util.toast.style;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.knight.wanandroid.library_util.toast.callback.ToastStyleInterface;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/8 15:48
 * @descript:
 */
public class ViewToastStyle implements ToastStyleInterface<View> {

    private final int mLayoutId;
    private final ToastStyleInterface<?> mStyle;

    public ViewToastStyle(int id, ToastStyleInterface<?> style) {
        mLayoutId = id;
        mStyle = style;
    }

    @Override
    public View createView(Context context) {
        return LayoutInflater.from(context).inflate(mLayoutId, null);
    }

    @Override
    public int getGravity() {
        if (mStyle == null) {
            return Gravity.CENTER;
        }
        return mStyle.getGravity();
    }

    @Override
    public int getXOffset() {
        if (mStyle == null) {
            return 0;
        }
        return mStyle.getXOffset();
    }

    @Override
    public int getYOffset() {
        if (mStyle == null) {
            return 0;
        }
        return mStyle.getYOffset();
    }

    @Override
    public float getHorizontalMargin() {
        if (mStyle == null) {
            return 0;
        }
        return mStyle.getHorizontalMargin();
    }

    @Override
    public float getVerticalMargin() {
        if (mStyle == null) {
            return 0;
        }
        return mStyle.getVerticalMargin();
    }
}
