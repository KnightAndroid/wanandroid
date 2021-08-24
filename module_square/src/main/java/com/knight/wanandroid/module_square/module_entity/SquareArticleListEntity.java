package com.knight.wanandroid.module_square.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/25 19:44
 * @descript:广场列表
 */
public final class SquareArticleListEntity extends BaseEntity {

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;

    private List<SquareArticleEntity> datas;

    public List<SquareArticleEntity> getDatas() {
        if (datas == null) {
            return new ArrayList<>();
        }
        return datas;
    }

    public void setDatas(List<SquareArticleEntity> datas) {
        this.datas = datas;
    }

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
}
