package com.knight.wanandroid.module_home.module_entity;

import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/24 14:04
 * @descript:
 */
public class EveryDayPushArticlesEntity{


    private int total;
    private boolean pushStatus;
    private String time;

    private List<EveryDayPushEntity> datas;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(boolean pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<EveryDayPushEntity> getDatas() {
        if (datas == null) {
            return new ArrayList<>();
        }
        return datas;
    }

    public void setDatas(List<EveryDayPushEntity> datas) {
        this.datas = datas;
    }
}
