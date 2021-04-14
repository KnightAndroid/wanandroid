package com.knight.wanandroid.module_home.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 10:02
 * @descript:
 */
public class SearchKeyWorksEntity extends BaseEntity {



    private String title;
    private int titleId;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }
}
