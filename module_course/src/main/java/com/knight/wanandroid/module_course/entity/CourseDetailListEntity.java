package com.knight.wanandroid.module_course.entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/16 10:57
 * Description:CourseDetailListEntity
 */
public class CourseDetailListEntity extends BaseEntity {


    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<CourseDetailEntity> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CourseDetailEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<CourseDetailEntity> datas) {
        this.datas = datas;
    }
}
