package com.knight.wanandroid.module_utils.entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * Author:Knight
 * Time:2022/5/12 14:44
 * Description:UtilsEntity
 */
public class UtilsEntity extends BaseEntity {
    private int id;
    private int isNew;
    private String link;
    private String name;
    private String tabName;
    private int showInTab;
    private String desc;
    private int visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getShowInTab() {
        return showInTab;
    }

    public void setShowInTab(int showInTab) {
        this.showInTab = showInTab;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
