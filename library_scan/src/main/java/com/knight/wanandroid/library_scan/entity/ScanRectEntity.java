package com.knight.wanandroid.library_scan.entity;

import java.io.Serializable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/19 10:18
 * @descript:
 */
public class ScanRectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int left;
    private int top;
    private int right;
    private int bottom;


    public ScanRectEntity(int left,int top,int right,int bottom){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }


    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
