package com.knight.wanandroid.module_set.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/10 15:20
 * @descript:
 */
public class LanguageSelectEntity extends BaseEntity {

    private String languageName;
    private String englishName;

    public String getEnglishName() {
        return englishName == null ? "" : englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    private boolean select;
    private boolean showLine;

    public String getLanguageName() {
        return languageName == null ? "" : languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
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
