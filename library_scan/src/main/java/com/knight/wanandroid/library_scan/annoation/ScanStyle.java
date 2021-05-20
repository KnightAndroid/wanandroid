package com.knight.wanandroid.library_scan.annoation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * @author apple
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({ScanStyle.NONE,ScanStyle.HALF_SCREEN, ScanStyle.FULL_SCREEN, ScanStyle.CUSTOMIZE})
public @interface ScanStyle {
    int NONE = -1;
    int HALF_SCREEN = 1001;
    int FULL_SCREEN = 1002;
    int CUSTOMIZE = 1003;
}
