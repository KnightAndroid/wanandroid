package com.knight.wanandroid.library_widget.floatmenu;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/9 10:27
 * @descript:
 */
public class Display {
    public static Point getScreenMetrics(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }
}
