package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import com.knight.wanandroid.library_common.utils.CacheUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/6 11:16
 * @descript:状态栏设置
 */
public class StatusBarUtils {

    /**
     *
     * 设置透明状态栏
     * @param activity
     */
    public static void transparentStatusBar(Activity activity){
        // 5.0以上系统状态栏透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                WindowInsetsController ic = activity.getWindow().getInsetsController();
                if(ic != null){
                    //让状态栏变亮 0,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS 让状态栏字体变白
                    if (CacheUtils.getNormalDark()) {
                        ic.setSystemBarsAppearance(0,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                    } else {
                        ic.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                    }
                    activity.getWindow().setDecorFitsSystemWindows(false);
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                if (CacheUtils.getNormalDark()) {
                    //白色字体
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

                } else {
                    //黑色字体
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     *
     * 返回状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


}
