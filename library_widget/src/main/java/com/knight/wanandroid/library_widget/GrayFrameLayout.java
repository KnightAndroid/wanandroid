package com.knight.wanandroid.library_widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 11:24
 * @descript:一行变灰布局
 */
public class GrayFrameLayout extends FrameLayout {

    private Paint mPaint = new Paint();

    public GrayFrameLayout(@NonNull Context context) {
        super(context);
    }

    public GrayFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(null, mPaint);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(null, mPaint);
        super.draw(canvas);
        canvas.restore();
    }


}
