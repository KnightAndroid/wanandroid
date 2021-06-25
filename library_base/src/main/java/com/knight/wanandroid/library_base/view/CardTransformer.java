package com.knight.wanandroid.library_base.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/24 14:52
 * @descript:
 */
public class CardTransformer implements ViewPager2.PageTransformer {

    /**
     * 动画类型 =》层叠
     */
    public final static int ANIM_TYPE_STACK = 1;
    /**
     * 动画类型 =》缩放
     */
    public final static int ANIM_TYPE_SCALE = 2;
    /**
     * 动画类型 =》风车
     */
    public final static int ANIM_TYPE_WINDMILL = 3;

    /**
     * 动画类型
     * 【ANIM_TYPE_STACK：层叠】
     * 【ANIM_TYPE_STACK：缩放】
     * 【ANIM_TYPE_WINDMILL：风车】
     */
    private int mTransformerType = ANIM_TYPE_STACK;

    /**
     * 初始位移量
     */
    private int mTranslation = 61;

    /**
     * 初始缩放比例
     */
    private int mScale = 80;

    /**
     * 初始旋转角度_风车
     */
    private float mRotation = -20;
    @Override
    public void transformPage(@NonNull View page, float position) {
        switch (mTransformerType) {

            // 动画类型 =》层叠
            case ANIM_TYPE_STACK:
                // 层叠
                animStack(page, position);
                break;

            // 动画类型 =》缩放
            case ANIM_TYPE_SCALE:
                // 缩放
                animScale(page, position);
                break;

            // 动画类型 =》风车
            case ANIM_TYPE_WINDMILL:
                // 风车
                animWindmill(page, position);
                break;
        }

    }

    /**
     * 设置动画类型
     *
     * @param type 动画类型
     *             【ANIM_TYPE_STACK：层叠】
     *             【ANIM_TYPE_SCALE：缩放】
     *             【ANIM_TYPE_WINDMILL：风车】
     * @return 需要预加载的页数
     */
    public int setTransformerType(int type) {
        mTransformerType = type;

        switch (type) {

            // 动画类型 =》层叠
            case ANIM_TYPE_STACK:
                // 层叠
                return 4;

            // 动画类型 =》缩放
            case ANIM_TYPE_SCALE:
                // 缩放
                return 2;

            // 动画类型 =》风车
            case ANIM_TYPE_WINDMILL:
                // 风车
                return 2;

            default:
                return 0;
        }
    }

    /**
     * 层叠
     *
     * @param view
     * @param position
     */
    private void animStack(View view, float position) {
        //缩放比例
        float scale = (view.getWidth() - mScale * position) / (float) (view.getWidth());

        // 设置卡片背景色
        if (view.getBackground() == null) {
            view.setBackgroundColor(0xFFFFFFFF);
        }
        // 设置Z轴及阴影
        view.setTranslationZ(scale * 20);
        // 设置横向缩放
        view.setScaleX(scale * 0.85f);
        // 设置纵向缩放
        view.setScaleY(scale * 0.85f);

        if (position <= 0.0f) {         // 当前页

            //X轴偏移
            view.setTranslationX((view.getWidth() / 3f * position));

            //打开点击事件
            view.setClickable(true);

        } else if (position > 0) {      // 后一页

            //X轴偏移
            view.setTranslationX((-view.getWidth() * position) + (mTranslation * position));

            //屏蔽点击事件
            view.setClickable(false);
        }
    }

    /**
     * 缩放
     *
     * @param view
     * @param position
     */
    private void animScale(View view, float position) {
        //缩放比例
        float scale = 1;

        if (position <= 0.0f) {         // 当前页

            // 计算缩放比例
            scale = (view.getWidth() + mScale * 5 * position) / (float) (view.getWidth());

            //X轴偏移
            view.setTranslationX(-(view.getWidth() / 3f * position));

            //打开点击事件
            view.setClickable(true);

        } else if (position > 0) {      // 后一页

            // 计算缩放比例
            scale = (view.getWidth() - mScale * 5 * position) / (float) (view.getWidth());

            //X轴偏移
            view.setTranslationX(-(view.getWidth() / 3f * position));

            //屏蔽点击事件
            view.setClickable(false);
        }

        // 设置卡片背景色
        if (view.getBackground() == null) {
            view.setBackgroundColor(0xFFFFFFFF);
        }
        // 设置Z轴及阴影
        view.setTranslationZ(scale * 20);
        // 设置横向缩放
        view.setScaleX(scale * 0.85f);
        // 设置纵向缩放
        view.setScaleY(scale * 0.85f);

    }

    /**
     * 风车
     *
     * @param view
     * @param position
     */
    private void animWindmill(View view, float position) {
        if (view.getBackground() == null) {
            // 设置横向缩放
            view.setScaleX(0.85f);
            // 设置纵向缩放
            view.setScaleY(0.85f);
        }

        if (position <= 0.0f) {         // 当前页

            // 旋转
            view.setRotation(mRotation * Math.abs(position));

            // Y轴位移
            view.setTranslationY(-(view.getHeight() / 10f * position));

            // Z轴 阴影
            float translationZ = (view.getWidth() + mScale * 5 * position) / (float) (view.getWidth());
            view.setTranslationZ(translationZ * 20);

            //打开点击事件
            view.setClickable(true);
        } else if (position > 0) {      // 后一页

            // 旋转
            view.setRotation(-(mRotation * Math.abs(position)));

            // Y轴位移
            view.setTranslationY((view.getHeight() / 10f * position));

            // Z轴 阴影
            float translationZ = (view.getWidth() - mScale * 5 * position) / (float) (view.getWidth());
            view.setTranslationZ(translationZ * 20);

            //屏蔽点击事件
            view.setClickable(false);
        }

        // 前后两页是否露角
        boolean showAngle = true;
        if (showAngle) {
            // X轴位移
            view.setTranslationX(-(view.getWidth() / 10f * position));
        } else {
            // X轴位移
            view.setTranslationX((view.getWidth() / 3f * position));
        }
    }
}
