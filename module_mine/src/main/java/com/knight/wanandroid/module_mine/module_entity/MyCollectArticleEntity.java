package com.knight.wanandroid.module_mine.module_entity;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 17:12
 * @descript:
 */
public class MyCollectArticleEntity extends BaseEntity implements MultiItemEntity {


    /**
     * author :
     * chapterId : 232
     * chapterName : 入门及知识点
     * courseId : 13
     * desc :
     * envelopePic :
     * id : 136602
     * link : https://www.jianshu.com/p/b799b63f29e2
     * niceDate : 2020-06-02 22:05
     * origin :
     * originId : 13737
     * publishTime : 1591106756000
     * title : Kotlin Coroutine 探索之旅
     * userId : 29303
     * visible : 0
     * zan : 0
     */

    private String author;
    private int chapterId;
    private String chapterName;
    private int courseId;
    private String desc;
    private String envelopePic;
    private int id;
    private String link;
    private String niceDate;
    private String origin;
    private int originId;
    private long publishTime;
    private String title;
    private int userId;
    private int visible;
    private int zan;
    private String shareUser;

    public String getShareUser() {
        return shareUser == null ? "" : shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    @Override
    public int getItemType() {
        if (TextUtils.isEmpty(envelopePic)) {
            return AppConfig.ARTICLE_TEXT_TYPE;
        }
        return AppConfig.ARTICLE_PICTURE_TYPE;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
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

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
