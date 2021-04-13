package com.knight.wanandroid.library_widget.loadcircleview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_widget.R;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/13 14:34
 * @descript:
 */
public class ProgressHUD {
    private ProgressHUDDialog mProgressHUDDialog;
    private float mDimAmount;
    private int mWindowColor;
    private float mCornerRadius;//矩形的圆角程度
    private Context mContext;

    public ProgressHUD(Context context,String text) {
        mContext = context;
        mProgressHUDDialog = new ProgressHUDDialog(context,text);
        mWindowColor = context.getResources().getColor(R.color.widget_progress_color);
        mCornerRadius = 10;
        //转圈圈的圆形view
        View view = new CirView(mContext);
        mProgressHUDDialog.setView(view);
    }

    //    设置HUD的大小
    public ProgressHUD setSize(int width, int height) {
        mProgressHUDDialog.setSize(width, height);
        return this;
    }


    //   设置矩形的圆角程度
    public ProgressHUD setCornerRadius(float radius) {
        mCornerRadius = radius;
        return this;
    }

    //展示
    public ProgressHUD show() {
        if (!isShowing()) {
            mProgressHUDDialog.show();
        }
        return this;
    }

    public boolean isShowing() {
        return mProgressHUDDialog != null && mProgressHUDDialog.isShowing();
    }
    //隐藏
    public void dismiss() {
        if (mProgressHUDDialog != null && mProgressHUDDialog.isShowing()) {
            mProgressHUDDialog.dismiss();
        }
    }

    private class ProgressHUDDialog extends Dialog {
        private View mView;

        private FrameLayout mCustomViewContainer;
        private BackgroundLayout mBackgroundLayout;
        private TextView tv_loading_show;
        private int mWidth, mHeight;
        private String tv_text = "";

        public ProgressHUDDialog(Context context,String tv_text) {
            super(context);
            this.tv_text = tv_text;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.kprogresshud_hud);
            Window window = getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = mDimAmount;
            layoutParams.gravity = Gravity.CENTER;
            window.setAttributes(layoutParams);
            setCanceledOnTouchOutside(false);
            initViews();
        }

        private void initViews() {
            mBackgroundLayout = (BackgroundLayout) findViewById(R.id.background);
            mBackgroundLayout.setBaseColor(mWindowColor);
            mBackgroundLayout.setCornerRadius(mCornerRadius);
            if (mWidth != 0) {
                updateBackgroundSize();
            }

            mCustomViewContainer = (FrameLayout) findViewById(R.id.container);
            tv_loading_show = findViewById(R.id.tv_loading_show);
            tv_loading_show.setText(tv_text);
            addViewToFrame(mView);
        }

        private void addViewToFrame(View view) {
            if (view == null) {
                return;
            }
            int wrapParam = ViewGroup.LayoutParams.WRAP_CONTENT;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(wrapParam, wrapParam);
            mCustomViewContainer.addView(view, params);
        }

        private void updateBackgroundSize() {
            ViewGroup.LayoutParams params = mBackgroundLayout.getLayoutParams();
            params.width = ScreenUtils.dp2px(getContext(),mWidth);
            params.height = ScreenUtils.dp2px(getContext(),mHeight);
            mBackgroundLayout.setLayoutParams(params);
        }

        public void setView(View view) {
            if (view != null) {
                mView = view;
                if (isShowing()) {
                    mCustomViewContainer.removeAllViews();
                    addViewToFrame(view);
                }
            }
        }

        public void setSize(int width, int height) {
            mWidth = width;
            mHeight = height;
            if (mBackgroundLayout != null) {
                updateBackgroundSize();
            }
        }
    }
}
