package com.knight.wanandroid.module_hierachy.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize 导航
 * @Date 2021/4/30 14:23
 * @descript:
 */
public class NavigateListEntity extends BaseEntity {
    private int cid;
    private String name;
    private List<NavigateChildrenEntity> articles;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NavigateChildrenEntity> getArticles() {
        if (articles == null) {
            return new ArrayList<>();
        }
        return articles;
    }

    public void setArticles(List<NavigateChildrenEntity> articles) {
        this.articles = articles;
    }
}
