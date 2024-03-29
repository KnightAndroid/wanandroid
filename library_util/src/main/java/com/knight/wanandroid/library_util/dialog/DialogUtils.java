package com.knight.wanandroid.library_util.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.R;

import androidx.appcompat.app.AlertDialog;

/**
 * @author created by knight
 * @organize wanandroid
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
    public  static AlertDialog.Builder getDialog(Context context){
        return new AlertDialog.Builder(context);
    }


    /**
     *
     * 一个按钮的信息对话框
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getConfirmMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.util_confim), onClickListener);
        return builder;
    }


    /**
     *
     * 两个按钮的信息框
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getConfirmDialog(Context context,String message,DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.util_confim),onClickListener);
        builder.setNegativeButton(context.getString(R.string.util_cancel),null);
        return builder;
    }



    /**
     *
     * 确定和取消都有点击事件
     * @param context
     * @param message
     * @param okClickListener
     * @param cancelClickListener
     * @return
     */
    public static void getConfirmDialog(Context context,String message,DialogInterface.OnClickListener okClickListener,DialogInterface.OnClickListener cancelClickListener){
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.util_confim),okClickListener);
        builder.setNegativeButton(context.getString(R.string.util_cancel),cancelClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(CacheUtils.getThemeColor());
    }


}
