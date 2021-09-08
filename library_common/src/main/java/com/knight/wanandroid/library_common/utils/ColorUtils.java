package com.knight.wanandroid.library_common.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import java.util.Random;

import androidx.annotation.NonNull;

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

    /**
     *
     * 颜色透明度
     * @param color
     * @param alpha
     * @return
     */
    public static int alphaColor(int color,float alpha) {
       int red  = Color.red(color);
       int green = Color.green(color);
       int blue = Color.blue(color);
       return Color.argb((int)(alpha * 255),red,green,blue);
    }


    /**
     *
     * 16进制 转RGB(String)类型颜色(无#号)
     * @param color
     * @return
     */
    public static String convertToRGB(int color) {
       String red = Integer.toHexString(Color.red(color));
       String green = Integer.toHexString(Color.green(color));
       String blue = Integer.toHexString(Color.blue(color));
       if (red.length() == 1) {
           red = "0" +red;
       }
       if (green.length() == 1) {
           green = "0" +green;
       }
       if (blue.length() == 1) {
           blue = "0" +blue;
       }
       return red + green +blue;
    }


    /**
     * ARGB(含RGB)颜色 转 16进制颜色
     *
     * @param argb ARGB(含RGB)颜色
     * @return 16进制颜色
     * @throws NumberFormatException 当{@param argb}不是一个正确的颜色格式的字符串时
     */
    public static int convertToColorInt(@NonNull String argb){
        if (argb.matches("[0-9a-fA-F]{1,6}")) {
            switch (argb.length()) {
                case 1:
                    return Color.parseColor("#00000" + argb);
                case 2:
                    return Color.parseColor("#0000" + argb);
                case 3:
                    char r = argb.charAt(0), g = argb.charAt(1), b = argb.charAt(2);
                    //noinspection StringBufferReplaceableByString
                    return Color.parseColor(new StringBuilder("#")
                            .append(r).append(r)
                            .append(g).append(g)
                            .append(b).append(b)
                            .toString());
                case 4:
                    return Color.parseColor("#00" + argb);
                case 5:
                    return Color.parseColor("#0" + argb);
                case 6:
                    return Color.parseColor("#" + argb);
                default:
                    return 0;
            }
        } else {
            return 0;
        }

    }

    /**
     *
     * 动态设置按钮选择颜色
     * @param checked 选择
     * @param normal 默认
     * @return
     */
    public static ColorStateList createColorStateList(int checked,int normal) {
        int[] colors = new int[] { checked,  normal};
        int[][] states = new int[2][];
        states[0] = new int[] { android.R.attr.state_checked , android.R.attr.state_enabled};
        states[1] = new int[] { android.R.attr.state_enabled };
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }



    /**
     * 过滤蓝光
     *
     * @param blueFilterPercent 蓝光过滤比例[10-30-80]
     */
    public static int getFilterColor(int blueFilterPercent) {
        int realFilter = blueFilterPercent;
        if (realFilter < 10) {
            realFilter = 10;
        } else if (realFilter > 80) {
            realFilter = 80;
        }
        int a = (int) (realFilter / 80f * 180);
        int r = (int) (200 - (realFilter / 80f) * 190);
        int g = (int) (180 - (realFilter / 80f) * 170);
        int b = (int) (60 - realFilter / 80f * 60);
        return Color.argb(a, r, g, b);
    }









}
