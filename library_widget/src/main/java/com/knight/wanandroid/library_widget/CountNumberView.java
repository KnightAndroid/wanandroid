package com.knight.wanandroid.library_widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/19 15:56
 * @descript:
 */
public final class CountNumberView extends androidx.appcompat.widget.AppCompatTextView {
    /**动画时长*/
    private int duration = 1800;
    /**显示数字*/
    private float number;
    /**显示表达式*/
    private String regex;

    /**不保留小数，整数*/
    public static final String INTREGEX = "%1$01.0f";
    /**保留2位小数*/
    public static final String FLOATREGEX = "%1$01.2f";

    public CountNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 显示带有动画效果的数字
     * @param number
     * @param regex
     */
    public void showNumberWithAnimation(float number, String regex) {
        if (TextUtils.isEmpty(regex)) {
            //默认为整数
            this.regex = INTREGEX;
        } else {
            this.regex = regex;
        }
        //修改number属性，会调用setNumber方法
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "number", 0, number);
        objectAnimator.setDuration(duration);
        //加速器，从慢到快到再到慢
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    /**
     * 获取当前数字
     * @return
     */
    public float getNumber() {
        return number;
    }

    /**
     * 根据正则表达式，显示对应数字样式
     * @param number
     */
    public void setNumber(float number) {
        this.number = number;
        setText(String.format(regex, number));
    }
}
