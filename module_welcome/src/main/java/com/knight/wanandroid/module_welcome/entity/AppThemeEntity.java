package com.knight.wanandroid.module_welcome.entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/3 17:31
 * @descript:
 */
public final class AppThemeEntity extends BaseEntity {
    private boolean gray;
    private String themeColor;
    private boolean forceTheme;

    public boolean isForceTheme() {
        return forceTheme;
    }

    public void setForceTheme(boolean forceTheme) {
        this.forceTheme = forceTheme;
    }

    public boolean isGray() {
        return gray;
    }

    public void setGray(boolean gray) {
        this.gray = gray;
    }

    public String getThemeColor() {
        return themeColor == null ? "" : themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }
}
