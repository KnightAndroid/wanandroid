package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/13 11:02
 * @descript:屏幕工具类
 */
public class ScreenUtils {

    /**
     *
     * dp转px
     * @param resources
     * @param dp
     * @return
     */
    public static float dp2px(Resources resources,float dp){
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    /**
     *
     * dp转px
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context,float dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,context.getResources().getDisplayMetrics());
    }

    /**
     *
     * sp转px
     * @param resources
     * @param sp
     * @return
     */
    public static float sp2px(Resources resources,float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     *
     * dp转px
     * @param dpValue
     * @return
     */
    public static int dp2px(final float dpValue){
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }

    /**
     *
     * 返回屏幕的宽
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity){
        DisplayMetrics dMetrics = activity.getResources().getDisplayMetrics();
        return dMetrics.widthPixels;
    }


    /**
     *
     * 返回屏幕的高
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity){
        DisplayMetrics dMetrics = activity.getResources().getDisplayMetrics();
        return dMetrics.heightPixels;
    }





}
