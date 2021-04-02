package com.knight.wanandroid.library_util;

import android.os.Build;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/2 11:13
 * @descript:手机帮助类 是否华为
 */
public class MobileUtils {

    public static boolean isEMUI(){
        String manufacturer = Build.MANUFACTURER;
        if("HUAWEI".equals(manufacturer)){
             return true;
        }
        return false;
    }

}
