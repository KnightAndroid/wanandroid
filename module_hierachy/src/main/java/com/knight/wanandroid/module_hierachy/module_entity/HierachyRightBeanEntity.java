package com.knight.wanandroid.module_hierachy.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 16:45
 * @descript:体系 导航 右边数据
 */
public class HierachyRightBeanEntity extends BaseEntity {
    private String name;
    private String titleName;
    private String tag;
    private boolean isTitle;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleName() {
        return titleName == null ? "" : titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTag() {
        return tag == null ? "" : tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }
}
