package com.wanandroid.knight.library_database.entity;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/4 15:58
 * @descript:
 */

@Entity(tableName = "historyreadrecords_table",indices = {@Index(value = "webUrl", unique = true),@Index(value = "articleId",unique = true)})
public class HistoryReadRecordsEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    //网页链接
    @NonNull
    private String webUrl;

    //网页文章
    private int articleId;

    //网页标题
    @NonNull
    private String title;

    //图片
    private String envelopePic;

    //时间插入
    @NonNull
    private Date insertTime;

    //作者
    private String author;

    //专栏
    private String chapterName;

    //描述
    private String desc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getWebUrl() {
        return webUrl == null ? "" : webUrl;
    }

    public void setWebUrl(@NonNull String webUrl) {
        this.webUrl = webUrl;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @NonNull
    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getEnvelopePic() {
        return envelopePic == null ? "" : envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    @NonNull
    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(@NonNull Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getAuthor() {
        return author == null ? "" : author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterName() {
        return chapterName == null ? "" : chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
