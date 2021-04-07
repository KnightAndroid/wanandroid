package com.knight.wanandroid.library_base.view;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 20:23
 * @descript:基类view
 */
public interface BaseView {
    void showLoading();
    void hideLoading();
    void showError(String errorMsg);
}
