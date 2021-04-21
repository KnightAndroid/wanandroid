package com.knight.wanandroid.library_widget;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Insets;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/21 17:16
 * @descript:转圈进度条
 */
public class ProgressDialog {


    private Dialog mDialog;
    private Activity context;
    private TextView tv_progress_tip;

    public ProgressDialog(@NonNull Activity context,String showMessage) {
        this.context = context;
        initDialog(showMessage);
    }


    private void initDialog(String showMessage){
        mDialog = new Dialog(context,R.style.MyProgressDialog);
        mDialog.setContentView(context.getLayoutInflater().inflate(R.layout.progress_dialog,null));
        tv_progress_tip = mDialog.findViewById(R.id.tv_progress_tip);
        tv_progress_tip.setText(showMessage);
        WindowManager.LayoutParams attributes = mDialog.getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = context.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            attributes.width = (int)((windowMetrics.getBounds().width() - insets.left - insets.right) * 0.8);
        } else {
            Display dd = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            dd.getMetrics(dm);
            attributes.width = (int)(dm.widthPixels * 0.8);
        }


    }

    public void showDialog(){
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dimissDialog(){
        if (mDialog != null) {
            mDialog.cancel();
        }
    }
}
