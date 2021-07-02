package com.knight.wanandroid.library_widget.flowlayout.listener;
import com.knight.wanandroid.library_widget.flowlayout.TagInfo;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/2 15:16
 * @descript:事件点击
 *
 */
public interface OnTagClickListener {
    void onTagClick(TagInfo tagInfo);

    void onTagDelete(TagInfo tagInfo);
}
