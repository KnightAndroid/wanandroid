package com.knight.wanandroid.library_util.dialog;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

/**
 * @author created by luguian
 * @organize 车童网
 * @Date 2021/4/21 16:53
 * @descript:
 */
public class DialogUtils {


    /**
     *
     * 获取Dialog
     * @param context
     * @return
     */
    public AlertDialog.Builder getDialog(Context context){
        return new AlertDialog.Builder(context);
    }

}
