package com.knight.wanandroid.library_widget.flowlayout.listener;

import android.widget.TextView;


/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/2 15:16
 * @descript:事件选择
 *
 */
public interface OnTagSelectListener {
    void onTagSelect(boolean isSelect, TextView view);

    void onSetEditDefaultColor(TextView view);
}
