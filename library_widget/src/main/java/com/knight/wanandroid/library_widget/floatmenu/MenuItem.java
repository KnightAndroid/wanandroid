package com.knight.wanandroid.library_widget.floatmenu;

import android.view.View;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/9 10:38
 * @descript:
 */
public class MenuItem {

    private String item;
    private int itemResId = View.NO_ID;


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getItemResId() {
        return itemResId;
    }

    public void setItemResId(int itemResId) {
        this.itemResId = itemResId;
    }
}
