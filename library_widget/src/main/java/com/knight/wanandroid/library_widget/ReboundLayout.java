package com.knight.wanandroid.library_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/21 10:12
 * @descript:越界回弹效果
 */
public class ReboundLayout extends FrameLayout {


    private ViewDragHelper mViewDragHelper;
    int damping = 3;//阻尼

    public ReboundLayout(Context context) {
        this(context, null);
    }

    public ReboundLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewDrag);
        if(array.getInteger(R.styleable.ViewDrag_damping , 0) != 0){
            damping = array.getInteger(R.styleable.ViewDrag_damping , 0);
        }

        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            private int mLeft = -1;
            private int mTop = -1;

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                if(mLeft == -1){
                    mLeft = capturedChild.getLeft();
                }

                if(mTop == -1){
                    mTop = capturedChild.getTop();
                }


            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                return child.getTop() + dy / damping;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //只允许上下拖动
                return mLeft;

            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                mViewDragHelper.settleCapturedViewAt(mLeft, mTop);
                invalidate();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public void setDamping(int damping){
        this.damping = damping;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
