package com.knight.wanandroid.library_scan.listener;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/19 11:22
 * @descript:
 */
public class PreviewTouchListener implements View.OnTouchListener {

    private ScaleGestureDetector mScaleGestureDetector;

    public PreviewTouchListener(Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, onScaleGestureListener);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }

    public interface CustomTouchListener {
        /**
         * 放大
         */
        void zoom(float delta);
    }

    private CustomTouchListener mCustomTouchListener;

    public void setCustomTouchListener(CustomTouchListener customTouchListener) {
        mCustomTouchListener = customTouchListener;
    }

    /**
     * 缩放监听
     */
    ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float delta = detector.getScaleFactor();
            if (mCustomTouchListener != null) {
                mCustomTouchListener.zoom(delta);
            }
            return true;
        }
    };
}
