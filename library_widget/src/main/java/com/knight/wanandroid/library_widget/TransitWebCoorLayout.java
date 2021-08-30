package com.knight.wanandroid.library_widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_common.utils.ColorUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/20 16:23
 * @descript:
 */
public class TransitWebCoorLayout extends CoordinatorLayout {

    private boolean darkTheme = false;
    private int bgColor = Color.TRANSPARENT;
    public TransitWebCoorLayout(@NonNull Context context) {
        this(context,null);
    }

    public TransitWebCoorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TransitWebCoorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        darkTheme = CacheUtils.getInstance().getNormalDark();
        if (darkTheme) {
            bgColor = ColorUtils.alphaColor(ContextCompat.getColor(getContext(),R.color.widget_night_color),0.6f);
        }
    }

    @Override
    public void dispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);
        if (darkTheme) {
            canvas.drawColor(bgColor);
        }

    }
}
