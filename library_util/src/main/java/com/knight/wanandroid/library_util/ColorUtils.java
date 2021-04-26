package com.knight.wanandroid.library_util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import java.util.Random;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 15:19
 * @descript:颜色配置
 */
public class ColorUtils {

    /**
     *
     * 随机生成颜色
     * @return
     */
    public static int getRandColorCode() {
        Random random = new Random();
        return 0xff000000 | random.nextInt(0x00ffffff);
    }

    /**
     *
     * 返回十六进制
      * @return
     */
    public static String getRandStringColorCode() {
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r:r;
        g = g.length() == 1 ? "0" + g:g;
        b = b.length() == 1 ? "0" + b:b;
        return r+g+b;
    }


    /**
     *
     * 返回colorList
     * @param context
     * @return
     */
    public static ColorStateList getOneColorStateList(Context context){
       int colors[] = {Color.parseColor("#55aff4")};
       int [][]states = new int[][]{new int[0]};
       return new ColorStateList(states,colors);
    }




}
