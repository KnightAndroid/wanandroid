package com.knight.wanandroid.library_util.toast.style;

import android.content.Context;
import android.view.View;

import com.knight.wanandroid.library_util.toast.callback.ToastStyleInterface;

/**
 * @author created by luguian
 * @organize wanandroid
 * @Date 2021/9/8 15:40
 * @descript:
 */
public class LocationToastStyle implements ToastStyleInterface<View> {

    private final ToastStyleInterface<?> mStyle;

    private final int mGravity;
    private final int mXOffset;
    private final int mYOffset;
    private final float mHorizontalMargin;
    private final float mVerticalMargin;

    public LocationToastStyle(ToastStyleInterface<?> style, int gravity) {
        this(style, gravity, 0, 0, 0, 0);
    }

    public LocationToastStyle(ToastStyleInterface<?> style, int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin) {
        mStyle = style;
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;
        mHorizontalMargin = horizontalMargin;
        mVerticalMargin = verticalMargin;
    }

    @Override
    public View createView(Context context) {
        return mStyle.createView(context);
    }

    @Override
    public int getGravity() {
        return mGravity;
    }

    @Override
    public int getXOffset() {
        return mXOffset;
    }

    @Override
    public int getYOffset() {
        return mYOffset;
    }

    @Override
    public float getHorizontalMargin() {
        return mHorizontalMargin;
    }

    @Override
    public float getVerticalMargin() {
        return mVerticalMargin;
    }
}
