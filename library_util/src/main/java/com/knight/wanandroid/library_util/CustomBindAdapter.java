package com.knight.wanandroid.library_util;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.databinding.BindingAdapter;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/27 18:12
 * @descript:
 */
public class CustomBindAdapter {

    @BindingAdapter({"checkChange"})
    public static void checkChange(CheckBox checkBox,CompoundButton.OnCheckedChangeListener listener) {
        checkBox.setOnCheckedChangeListener(listener);

    }
}
