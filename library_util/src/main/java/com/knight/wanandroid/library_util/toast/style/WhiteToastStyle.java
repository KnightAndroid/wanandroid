package com.knight.wanandroid.library_util.toast.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/8 15:52
 * @descript:
 */
public class WhiteToastStyle extends BlackToastStyle {
    @Override
    protected int getTextColor(Context context) {
        return 0XBB000000;
    }

    @Override
    protected Drawable getBackgroundDrawable(Context context) {
        GradientDrawable drawable = new GradientDrawable();
        // 设置颜色
        drawable.setColor(0XFFEAEAEA);
        // 设置圆角
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics()));
        return drawable;
    }
}
