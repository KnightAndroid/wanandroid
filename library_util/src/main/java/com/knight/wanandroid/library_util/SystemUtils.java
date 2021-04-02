package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/2 14:56
 * @descript:
 */
public class SystemUtils {

    /**
     *
     * 隐藏软键盘
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     *
     * 显示软键盘
     * @param activity
     * @param view
     */
    public static void showSoftKeyBoard(Activity activity,View view){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        }
    }






}
