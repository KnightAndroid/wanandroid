package com.knight.wanandroid.library_widget.lottie;

import android.animation.TimeInterpolator;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/17 13:54
 * @descript:
 */
public class ActionBarInterpolator implements TimeInterpolator {

    private static final float FIRST_BOUNCE_PART = 0.375f;
    private static final float SECOND_BOUNCE_PART = 0.625f;

    @Override
    public float getInterpolation(float input) {
        if (input < FIRST_BOUNCE_PART) {
            return (-28.4444f) * input * input + 10.66667f * input;
        } else if (input < SECOND_BOUNCE_PART) {
            return (21.33312f) * input * input - 21.33312f * input + 4.999950f;
        } else {
            return (-9.481481f) * input * input + 15.40741f * input - 5.925926f;
        }
    }
}
