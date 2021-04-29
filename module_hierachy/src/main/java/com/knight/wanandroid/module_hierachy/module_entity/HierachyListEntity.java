package com.knight.wanandroid.module_hierachy.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 15:24
 * @descript:体系数据模型
 */
public class HierachyListEntity extends BaseEntity  {

    private String name;


    /*体系*/
    private String courseId;
    private String id;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;
    private List<HierachyChildrenEntity> children;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseId() {
        return courseId == null ? "" : courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<HierachyChildrenEntity> getChildren() {
        if (children == null) {
            return new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<HierachyChildrenEntity> children) {
        this.children = children;
    }



}
