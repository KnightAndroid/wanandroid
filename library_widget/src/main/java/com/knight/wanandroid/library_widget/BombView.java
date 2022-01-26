package com.knight.wanandroid.library_widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.knight.wanandroid.library_util.BitmapUtils;
import com.knight.wanandroid.library_util.WeakRefHandler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Author:Knight
 * Time:2022/1/25 16:25
 * Description:BombView
 */
public class BombView extends SurfaceView implements SurfaceHolder.Callback {

    private static final int MAX_BUBBLE_COUNT = 90;

    private SurfaceHolder mHolder;
    private DrawTask mDrawTask; // 绘制UI的线程
    private Paint mPaint; // 绘制需要使用的画刷

    private FuseView mFuseView; // 引导的view

    private boolean mIsDismiss = false; //是否处于消失阶段

    private int mWidth; // 控件的宽度
    private int mHeight; // 控件的高度

    private int mBombX; // 旋转的中心X
    private int mBombY; // 旋转的中心Y

    private int dx = 0; // 引导view在坐抛物线运动时在x轴的增量

    private Bitmap[] mDrawables; // 存放需要展示的图
    private int[] mDrawableResIDs; // 存放需要展示的图

    private List<Bubble> mBubbles = Collections.synchronizedList(new LinkedList<Bubble>()); // 用于存放点赞信息

    private Random mRandom = new Random(); // 用于产生随机数

    private static final int MSG_DRAW_BUBBLE = 10;
    private static HandlerThread mHandlerThread = new HandlerThread("BombView");

    static {
        mHandlerThread.start();
    }


    public interface BombStatusListener{
        void onAnimationStart();
        void onAnimationEnd();
    }

    private BombStatusListener mBombStatusListener;

    public BombView setBombStatusListener(BombStatusListener mBombStatusListener) {
        this.mBombStatusListener = mBombStatusListener;
        return this;
    }



    private Handler.Callback mCallback = new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DRAW_BUBBLE:
                    mHandler.removeMessages(MSG_DRAW_BUBBLE);
                    mHandler.post(mDrawTask);
                    mHandler.sendEmptyMessageDelayed(MSG_DRAW_BUBBLE, 20);
                    break;
            }
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, mHandlerThread.getLooper());

    public BombView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setKeepScreenOn(true);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mPaint = new Paint();

        mDrawableResIDs = new int[]{
                R.drawable.widget_praise_eight,
                R.drawable.widget_praise_one,
                R.drawable.widget_praise_third,
                R.drawable.widget_praise_two,
                R.drawable.widget_praise_five,
                R.drawable.widget_praise_four,
                R.drawable.widget_praise_seven,
                R.drawable.widget_praise_six,
        };
        mDrawables = new Bitmap[mDrawableResIDs.length];
    }

    @Override
    protected void onDraw(final Canvas canvas) {

        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // 清空界面

        if (mFuseView == null || mFuseView.bitmap == null) {
            return;
        }

        if (mFuseView.y + mFuseView.bitmap.getHeight() / 2 > mBombY) {
            mPaint.setAlpha(255);
            if (mFuseView.x + mFuseView.bitmap.getWidth() / 2 > mWidth / 2) { // 抛物线
                mFuseView.scale = dx * 1.5f / mWidth + 1f;

                canvas.save();
                mFuseView.x = mWidth * 7 / 8 - dx;
                mFuseView.y = (int) getY(dx);
                canvas.scale(mFuseView.scale, mFuseView.scale, mFuseView.x + mFuseView.bitmap.getWidth() / 2, mFuseView.y + mFuseView.bitmap.getHeight() / 2);
                canvas.drawBitmap(mFuseView.bitmap, mFuseView.x, mFuseView.y, mPaint);
                canvas.restore();
                dx += dip2px(4);

            } else { // 直线上升
                canvas.save();
                canvas.scale(mFuseView.scale, mFuseView.scale, mFuseView.x + mFuseView.bitmap.getWidth() / 2, mFuseView.y + mFuseView.bitmap.getHeight() / 2);
                canvas.drawBitmap(mFuseView.bitmap, mFuseView.x, mFuseView.y, mPaint);
                canvas.restore();
                mFuseView.y -= dip2px(5);
            }
        } else {
            if (!mIsDismiss) {
                mIsDismiss = mBubbles.size() > 0 && mBubbles.get(0).top < mWidth * 9 / 20;
            }

            synchronized (mBubbles) {
                for (int i = mBubbles.size() - 1; i >= 0 && mBubbles.size() > 0; i--) { // 绘制气泡
                    drawBubble(canvas, mBubbles.get(i));
                }
            }

            // 大爱心放大消失
            mFuseView.x = mBombX - mFuseView.bitmap.getWidth() / 2;
            mFuseView.y = mBombY - mFuseView.bitmap.getHeight() / 2;
            mFuseView.scale += 0.2;
            mFuseView.alpha = (int) ((5 - mFuseView.scale) * 85);// ps:85 = 255/3 根据缩放倍数来计算透明度
            if (mFuseView.alpha > 0) {
                mPaint.setAlpha(mFuseView.alpha);
                canvas.save();
                canvas.scale(mFuseView.scale, mFuseView.scale, mBombX, mBombY);
                canvas.drawBitmap(mFuseView.bitmap, mFuseView.x, mFuseView.y, mPaint);
                canvas.restore();
            }
            if (mBubbles.size() <= 0) {
                if (mBombStatusListener != null) {
                    mBombStatusListener.onAnimationEnd();
                }
                dismiss();
            }
        }
    }

    private void drawBubble(Canvas canvas, Bubble bubble) {
        if (bubble.top + bubble.bitmap.getHeight() / 2 > mWidth / 2) {
            bubble.top = bubble.top - dip2px(2);
            return;
        }

        if (mIsDismiss) {
            bubble.alpha -= 12;
            if (bubble.alpha < 0) {
                mBubbles.remove(bubble);
                return;
            }
        }
        bubble.top -= dip2px(1.2f);
        if (bubble.scale < 1.2f) {
            bubble.scale += 0.015f;
        }

        mPaint.setAlpha(bubble.alpha);
        canvas.save();
        canvas.scale(bubble.scale, bubble.scale, bubble.left + bubble.bitmap.getWidth() / 2, bubble.top + bubble.bitmap.getHeight() / 2);
        canvas.rotate(bubble.rotate, mBombX, mBombY);
        canvas.drawBitmap(bubble.bitmap, bubble.left, bubble.top, mPaint);
        canvas.restore();
    }

    /**
     * 开始绘制爆炸彩蛋，如果上一个效果还没结束，则不处理新的
     */
    public void startBomb() {
        if (mFuseView != null) {
            return;
        }
        initFuseView();
        setVisibility(View.VISIBLE);
        if (mBombStatusListener != null) {
            mBombStatusListener.onAnimationStart();
        }
        generateBubble(MAX_BUBBLE_COUNT);
        dx = 0;
        mIsDismiss = false;
        mHandler.sendEmptyMessage(MSG_DRAW_BUBBLE);

    }

    /**
     * 初始化引导
     */
    private void initFuseView() {
        mFuseView = new FuseView();
        mFuseView.x = mWidth * 7 / 8;
        mFuseView.y = mHeight * 17 / 20;
        mFuseView.bitmap = BitmapUtils.readBitmap(getResources(), R.drawable.widget_praise_five);
    }


    /**
     * 添加点赞，会对传入的个数进行处理
     *
     * @param count
     */
    private void generateBubble(int count) {
        for (int i = 0; i < count; i++) {
            Bubble bubble = new Bubble();
            bubble.bitmap = getRandBitmap();
            bubble.alpha = 155 + mRandom.nextInt(100);
            bubble.scale = 0.6f + mRandom.nextFloat() * 0.4f;
            bubble.rotate = 360 * i / count; // 由于绘制时还需要缩放，不然可以使用增量旋转，即不用每次都restore
            if (bubble.rotate > 180) { // 反向旋转
                bubble.rotate = bubble.rotate - 360;
            }
            bubble.left = mWidth / 2 - bubble.bitmap.getWidth() / 2;

            float offset = 0;
            if (i % 2 == 0) {
                offset = mWidth * 0.1f + mWidth * 0.15f * i / count;
            }
            if (i % 3 == 0) {
                offset = mWidth * 0.25f + mWidth * 0.15f * i / count;
            }
            if (i % 5 == 0) {
                offset = mWidth * 0.4f + mWidth * 0.12f * mRandom.nextFloat();
            }
            bubble.top = offset - bubble.bitmap.getHeight();
            mBubbles.add(0, bubble);
        }
        mBubbles.get(0).top = mWidth * 0.52f - dip2px(5); // 以第一个气泡的位置判断是否该dismiss
        mBubbles.get(0).alpha = 1; // 防止正在消失时突然显现出来
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mDrawTask == null) {
            mDrawTask = new DrawTask(holder, this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.mWidth = width;
        this.mHeight = height;

        mBombX = mWidth / 2;
        mBombY = mWidth / 2;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    /**
     * 随机获取一个bitmap
     *
     * @return
     */
    private Bitmap getRandBitmap() {
        int n = mRandom.nextInt(mDrawableResIDs.length);
        Bitmap bitmap = mDrawables[n];
        if (bitmap == null || bitmap.isRecycled()) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inMutable = true;
            bitmap = BitmapUtils.readBitmap(getResources(), mDrawableResIDs[n], opts);
            mDrawables[n] = bitmap;
        }
        return bitmap;
    }

    /**
     * 绘制UI的线程，只要是调用PraiseView.onDraw(canvas);
     * 并且做了锁保护（固定用法，不要轻易修改）
     * ###########################################
     */
    class DrawTask implements Runnable {

        private SurfaceHolder holder;
        private BombView bombView;

        public DrawTask(SurfaceHolder holder, BombView bombView) {
            this.bombView = bombView;
            this.holder = holder;
        }

        @SuppressLint("WrongCall")
        @Override
        public void run() {
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    bombView.onDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    try { // 修复umeng崩溃
                        holder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    class Bubble {
        public Bitmap bitmap;

        public float scale = 1.0f; // 缩放
        public float top = 0f; // 偏移
        public float left = 0f; // 偏移
        public int rotate = 0; // 旋转
        public int alpha = 255; // 透明度


        public Bubble() {
            this.bitmap = getRandBitmap();
        }
    }

    class FuseView {
        public Bitmap bitmap;
        public float scale;
        public int alpha;
        public int x;
        public int y;
    }

    private float getY(float x) {
        return mHeight * 10 / 11 - 0.009f * x * x;
    }

    private void dismiss() {
        mHandler.removeCallbacksAndMessages(null);
        post(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        });
        mFuseView = null;
        for (Bitmap bitmap : mDrawables) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        mBubbles.clear();
        dismiss();
    }

    public float dip2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }
}
