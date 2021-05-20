package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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
            e.printStackTrace();
        }
    }


    /**
     *
     * 监听输入框
     * @param et
     * @param tv
     */
    public static void seteditTextChangeListener(EditText et, TextView tv){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!s.toString().equals("")) {
                    tv.setText("搜索");
                } else {
                    tv.setText("取消");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    tv.setText("搜索");
                } else {
                    tv.setText("取消");
                }
            }
        });
    }


    /**
     *
     * 复制文本到剪切板
     */
    public static void copyContent(Context context,String copyText) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyText);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

    }






}
