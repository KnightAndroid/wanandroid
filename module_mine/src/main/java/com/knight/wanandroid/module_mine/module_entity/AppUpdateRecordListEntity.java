package com.knight.wanandroid.module_mine.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/15 16:11
 * @descript:
 */
public final class AppUpdateRecordListEntity extends BaseEntity {
    private int curPage;
    private List<AppUpdateRecordEntity> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<AppUpdateRecordEntity> getDatas() {
        if (datas == null) {
            return new ArrayList<>();
        }
        return datas;
    }

    public void setDatas(List<AppUpdateRecordEntity> datas) {
        this.datas = datas;
    }
}
