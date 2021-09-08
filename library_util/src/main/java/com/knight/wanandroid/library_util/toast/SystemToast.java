package com.knight.wanandroid.library_util.toast;

import android.app.Application;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.knight.wanandroid.library_util.toast.callback.ToastInterface;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/8 16:07
 * @descript:
 */

public class SystemToast extends Toast implements ToastInterface {

    /** 吐司消息 View */
    private TextView mMessageView;

    public SystemToast(Application application) {
        super(application);
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        if (view == null) {
            mMessageView = null;
            return;
        }
        mMessageView = findMessageView(view);
    }

    @Override
    public void setText(CharSequence text) {
        super.setText(text);
        if (mMessageView == null) {
            return;
        }
        mMessageView.setText(text);
    }
}
