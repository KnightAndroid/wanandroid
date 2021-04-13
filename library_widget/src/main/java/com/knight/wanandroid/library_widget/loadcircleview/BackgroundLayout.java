package com.knight.wanandroid.library_widget.loadcircleview;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_widget.R;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/13 14:43
 * @descript:请求进度条
 */
public class BackgroundLayout extends LinearLayout {


    private float mCornerRadius;
    private int mBackgroundColor;

    public BackgroundLayout(Context context) {
        super(context);
        init();
    }

    public BackgroundLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    @SuppressWarnings("deprecation")
    private void init() {
        int color = getContext().getResources().getColor(R.color.widget_progress_color);
        initBackground(color, mCornerRadius);
    }


    private void initBackground(int color, float cornerRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(cornerRadius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void setCornerRadius(float radius) {
        mCornerRadius = ScreenUtils.dp2px(getContext(),radius);
        initBackground(mBackgroundColor, mCornerRadius);
    }

    public void setBaseColor(int color) {
        mBackgroundColor = color;
        initBackground(mBackgroundColor, mCornerRadius);
    }
}