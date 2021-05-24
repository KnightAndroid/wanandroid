package com.knight.wanandroid.library_widget.pagertransformer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/21 15:39
 * @descript:
 */
public class AspectRatioCradView extends CardView {
    private float ratio = 1.2f;
    public AspectRatioCradView(@NonNull Context context) {
        this(context, null);
    }

    public AspectRatioCradView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioCradView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (ratio > 0) {
            int ratioHeight = (int) (getMeasuredWidth() * ratio);
            setMeasuredDimension(getMeasuredWidth(), ratioHeight);
            ViewGroup.LayoutParams lp = getLayoutParams();
         //   LayoutParams lp = (LayoutParams) getLayoutParams();
           // lp.setMargins(30,0,30,0);
            lp.height = ratioHeight;
            setLayoutParams(lp);
        }
    }
}
