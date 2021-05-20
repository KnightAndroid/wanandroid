package com.knight.wanandroid.library_scan.annoation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/19 10:25
 * @descript:
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({ScanMode.RESTART, ScanMode.REVERSE})
public @interface ScanMode {
    int RESTART = 1;//重复运动
    int REVERSE = 2;//往返运动
}
