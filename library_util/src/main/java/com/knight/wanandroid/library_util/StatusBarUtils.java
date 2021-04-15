package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.WindowManager;

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
                    ic.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                   // activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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


}
