package com.knight.wanandroid.module_message.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/30 14:50
 * @descript:
 */
public class MessageEntity extends BaseEntity {


    /**
     * category : 999
     * date : 1584978588000
     * fromUser : 鸿洋
     * fromUserId : 2
     * id : 8492
     * isRead : 1
     * link : https://www.wanandroid.com/wenda/show/12489
     * message : 「每日一问  自定义控件测量模式真的和 match_parent,wrap_content 一一对应吗？」，这是我们第一条广播的站内消息，在这个标志性的时刻，给大家说句话：感谢每一位赞助过wanandroid的同学，wanandroid 因你们而更加美好，新一年的服务器我也已经完成续费，谢谢大家！
     * niceDate : 2020-03-23 23:49
     * tag : 系统消息
     * title : 更新了新的问答
     * userId : 8419
     */

    private int category;
    private long date;
    private String fromUser;
    private int fromUserId;
    private int id;
    private int isRead;
    private String link;
    private String message;
    private String niceDate;
    private String tag;
    private String title;
    private int userId;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
