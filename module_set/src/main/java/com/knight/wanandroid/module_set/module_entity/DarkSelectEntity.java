package com.knight.wanandroid.module_set.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/19 11:30
 * @descript:
 */
public final class DarkSelectEntity extends BaseEntity {
    private String name;
    private String englishName;
    private boolean select;

    public String getEnglishName() {
        return englishName == null ? "" : englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    private boolean isDark;
    private boolean showLine;

    public boolean isDark() {
        return isDark;
    }

    public void setDark(boolean dark) {
        isDark = dark;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }


    public boolean isShowLine() {
        return showLine;
    }

    public void setShowLine(boolean showLine) {
        this.showLine = showLine;
    }
}
