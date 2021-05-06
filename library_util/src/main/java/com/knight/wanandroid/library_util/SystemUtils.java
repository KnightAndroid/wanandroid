package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
     * 延迟显示软键盘
     * @param view
     */
    public static void showDelaySoftKeyBoard(View view){
        new Handler(Looper.getMainLooper()).postDelayed(()->{

            showSoftKeyBoard(view);
        },500);

    }








    /**
     *
     * 显示软键盘
     * @param view
     */
    public static void showSoftKeyBoard(View view){
        try {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);

        } catch (Exception e) {
            Log.e("TAG", "********************" + e.toString());
        }
    }






}
