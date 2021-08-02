package com.knight.wanandroid.module_set.module_annoation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/2 17:17
 * @descript:
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({ColorStyle.THEMECOLOR,ColorStyle.TEXTCOLOR, ColorStyle.BGCOLOR})
public @interface ColorStyle {
    int THEMECOLOR = 5001;
    int TEXTCOLOR = 5002;
    int BGCOLOR = 5003;
}
