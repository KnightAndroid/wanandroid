package com.knight.wanandroid.library_widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;

import com.knight.wanandroid.library_util.ScreenUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/7 19:35
 * @descript:沉浸式 Toobar
 */
public class CompatToobar extends Toolbar {
    public CompatToobar(Context context) {
        this(context,null,0);
    }

    public CompatToobar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CompatToobar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }


    private void setUp() {
        int compatPaddingTop = 0;
        // android 4.4以上将Toolbar添加状态栏高度的上边距，沉浸到状态栏下方
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
             compatPaddingTop = getStatusBarHeight();
        }

        this.setPadding(getPaddingLeft(), ScreenUtils.dp2px(10) +compatPaddingTop,getPaddingRight(),getPaddingBottom() + compatPaddingTop / 2);
    }


    public int getStatusBarHeight() {
        int result = 0;
        try {
            int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = Resources.getSystem().getDimensionPixelSize(resourceId);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


}
