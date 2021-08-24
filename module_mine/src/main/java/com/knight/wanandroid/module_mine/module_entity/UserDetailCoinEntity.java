package com.knight.wanandroid.module_mine.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 10:43
 * @descript:个人积分明细
 */
public final class UserDetailCoinEntity extends BaseEntity {


    /**
     * coinCount : 11
     * date : 1619145676608
     * desc : 2021-04-23 10:41:16 签到 , 积分：10 + 1
     * id : 417695
     * reason : 签到
     * type : 1
     * userId : 96500
     * userName : test2001
     */

    private int coinCount;
    private long date;
    private String desc;
    private int id;
    private String reason;
    private int type;
    private int userId;
    private String userName;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
