package com.knight.wanandroid.module_set.module_dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.EditText;
import android.widget.TextView;

import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_widget.ColorPickerView;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.module_annoation.ColorStyle;

import java.util.Locale;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/27 15:08
 * @descript:
 */
public final class ColorPickerDialog extends Dialog implements ColorPickerView.OnColorChangedListener, View.OnClickListener {
    private OnColorPickedListener mListener;
    private ColorPickerView set_color_picker_view;
    private EditText et_color;
    private View view_color_panel;
    private TextView tv_cancel;
    private TextView tv_confim;
    private TextView set_tv_recovertheme;
    private Context mContext;
    private int colorStyle;
    private String recoverText;


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_confim) {
            if (mListener != null) {
                if (ColorUtils.convertToColorInt(et_color.getText().toString().trim()) != 0) {
                    mListener.onColorPicked(((ColorDrawable) view_color_panel.getBackground()).getColor());
                }
            }
            dismiss();
        } else if (v.getId() == R.id.set_tv_recovertheme) {
            switch (colorStyle) {
                case ColorStyle.THEMECOLOR:
                    view_color_panel.setBackgroundColor(Color.parseColor("#55aff4"));
                    et_color.setText("55aff4");
                    break;
                case ColorStyle.TEXTCOLOR:
                    view_color_panel.setBackgroundColor(Color.parseColor("#333333"));
                    et_color.setText("333333");
                    break;
                case ColorStyle.BGCOLOR:
                    view_color_panel.setBackgroundColor(Color.parseColor("#f9f9f9"));
                    et_color.setText("f9f9f9");
                    break;
                default:
                    break;
            }

        } else {
            dismiss();
        }

    }

    public interface OnColorPickedListener {
        void onColorPicked(int color);
    }


    public void setOnColorChangedListener(OnColorPickedListener mListener) {
        this.mListener = mListener;

    }

    private ColorPickerDialog(Context context, int initialColor, int colorStyle, String recoverText) {
        super(context);
        this.mContext = context;
        this.colorStyle = colorStyle;
        this.recoverText = recoverText;
        setUp(initialColor);
    }


    private void setUp(int color) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.set_colorpicker_dialog, null);
        setContentView(layout);
        // 必须设置这两个,才能设置宽度
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = ((Activity) mContext).getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            attributes.width = (int) ((windowMetrics.getBounds().width() - insets.left - insets.right) * 0.9);
        } else {
            Display dd = ((Activity) mContext).getWindowManager().getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            dd.getMetrics(dm);
            attributes.width = (int) (dm.widthPixels * 0.9);
        }
        set_color_picker_view = (ColorPickerView) layout.findViewById(R.id.set_color_picker_view);
        et_color = layout.findViewById(R.id.et_color);
        view_color_panel = layout.findViewById(R.id.view_color_panel);
        tv_cancel = layout.findViewById(R.id.tv_cancel);
        tv_confim = layout.findViewById(R.id.tv_confim);
        set_tv_recovertheme = layout.findViewById(R.id.set_tv_recovertheme);
        set_tv_recovertheme.setText(recoverText);
        tv_cancel.setOnClickListener(this);
        tv_confim.setOnClickListener(this);
        set_tv_recovertheme.setOnClickListener(this);

        set_color_picker_view.setOnColorChangedListener(this);
        set_color_picker_view.setColor(color, true);
        et_color.setText(ColorUtils.convertToRGB(color).toUpperCase(Locale.getDefault()));
        et_color.addTextChangedListener(mTextWatcher);


    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            view_color_panel.setBackgroundColor(ColorUtils.convertToColorInt(et_color.getText().toString().trim()));
        }
    };

    public int getColor() {
        return set_color_picker_view.getColor();
    }

    @Override
    public void onColorChanged(int color) {
        view_color_panel.setBackgroundColor(color);
        et_color.setText(ColorUtils.convertToRGB(color).toUpperCase(Locale.getDefault()));
    }


    public static class Builder {
        private Context mContext;
        private int initColor;
        private String recoverText;
        private int colorStyle;
        private OnColorPickedListener mListener;

        public Builder(Context mContext, int initColor, int colorStyle, String recoverText) {
            this.mContext = mContext;
            this.initColor = initColor;
            this.recoverText = recoverText;
            this.colorStyle = colorStyle;
        }

        public Builder setOnColorPickedListener(OnColorPickedListener mListener) {
            this.mListener = mListener;
            return this;

        }

        public ColorPickerDialog build() {
            ColorPickerDialog dialog = new ColorPickerDialog(mContext, initColor, colorStyle, recoverText);
            dialog.setOnColorChangedListener(mListener);
            return dialog;
        }
    }
}
