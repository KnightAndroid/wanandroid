package com.knight.wanandroid.library_widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.widget.NestedScrollView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/13 15:13
 * @descript:首页布局
 */
public class CoordinatorScrollView extends NestedScrollView implements NestedScrollingParent2 {

    private int maxScrollY = 0;

    public CoordinatorScrollView(@NonNull Context context) {
        super(context);
    }

    public CoordinatorScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatorScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setMaxScrollY(int maxScrollY) {
        this.maxScrollY = maxScrollY;

    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target,  int axes,
                                       int type){
            return true;
    }


    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
                                       int type){


        if (dy > 0 && getScrollY() < maxScrollY) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }
}
