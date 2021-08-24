package com.knight.wanandroid.module_home.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/31 14:49
 * @descript:
 */
public final class OpenSourceEntity extends BaseEntity {


    private String name;
    private String desc;

    public String getAbroadlink() {
        return abroadlink == null ? "" : abroadlink;
    }

    public void setAbroadlink(String abroadlink) {
        this.abroadlink = abroadlink;
    }

    public String getInternallink() {
        return internallink == null ? "" : internallink;
    }

    public void setInternallink(String internallink) {
        this.internallink = internallink;
    }

    private String abroadlink;
    private String internallink;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
