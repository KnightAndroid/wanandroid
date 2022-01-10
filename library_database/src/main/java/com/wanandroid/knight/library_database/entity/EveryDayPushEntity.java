package com.wanandroid.knight.library_database.entity;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/16 14:27
 * @descript:
 */

@Entity(tableName = "everydaypush_table")
public class EveryDayPushEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String articlePicture;

    @NonNull
    private String articleLink;
    @NonNull
    private String time;
    private String author;
    private boolean pushStatus;

    public boolean isPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(boolean pushStatus) {
        this.pushStatus = pushStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticlePicture() {
        return articlePicture == null ? "" : articlePicture;
    }

    public void setArticlePicture(String articlePicture) {
        this.articlePicture = articlePicture;
    }

    @NonNull
    public String getArticleLink() {
        return articleLink == null ? "" : articleLink;
    }

    public void setArticleLink(@NonNull String articleLink) {
        this.articleLink = articleLink;
    }

    @NonNull
    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author == null ? "" : author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticledesc() {
        return articledesc == null ? "" : articledesc;
    }

    public void setArticledesc(String articledesc) {
        this.articledesc = articledesc;
    }

    public String getArticleTitle() {
        return articleTitle == null ? "" : articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getPopupTitle() {
        return popupTitle == null ? "" : popupTitle;
    }

    public void setPopupTitle(String popupTitle) {
        this.popupTitle = popupTitle;
    }

    private String articledesc;
    private String articleTitle;
    private String popupTitle;
}
