package com.knight.wanandroid.module_home.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author created by knight
 * @organize 置顶文章
 * @Date 2021/4/8 19:38
 * @descript:置顶文章数据模型
 */
public class TopArticleEntity extends BaseEntity {
    /**
     * apkLink :
     * audit : 1
     * author : xiaoyang
     * canEdit : false
     * chapterId : 440
     * chapterName : 官方
     * collect : false
     * courseId : 13
     * desc : <p>对于 Intent 大家肯定都不陌生，今天我们聊一聊它的一个近亲：<strong>PendingIntent</strong></p>
     <p>官方对其描述为：</p>
     <p>A description of an Intent and target action to perform with it. </p>
     <p>那么问题来了：</p>
     <ol>
     <li>PendingIntent 仅仅是对 Intent 的一个封装吗？</li>
     <li>如果1 不是，那么为什么 PendingIntent与 Intent 有何不同，它的定位是什么样的呢？</li>
     <li>对于 2 的回答，如果从源码侧佐证？</li>
     </ol>
     * descMd :
     * envelopePic :
     * fresh : false
     * host :
     * id : 18072
     * link : https://www.wanandroid.com/wenda/show/18072
     * niceDate : 2021-04-24 23:09
     * niceShareDate : 2021-04-24 22:58
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1619276960000
     * realSuperChapterId : 439
     * selfVisible : 0
     * shareDate : 1619276317000
     * shareUser :
     * superChapterId : 440
     * superChapterName : 问答
     * tags : [{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}]
     * title : 每日一问 | 已经有了 Intent，那为啥还要 PendingIntent?
     * type : 1
     * userId : 2
     * visible : 1
     * zan : 7
     */

    private String apkLink;
    private int audit;
    private String author;
    private boolean canEdit;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String descMd;
    private String envelopePic;
    private boolean fresh;
    private String host;
    private int id;
    private String link;
    private String niceDate;
    private String niceShareDate;
    private String origin;
    private String prefix;
    private String projectLink;
    private long publishTime;
    private int realSuperChapterId;
    private int selfVisible;
    private long shareDate;
    private String shareUser;
    private int superChapterId;
    private String superChapterName;
    private List<TagsDTO> tags;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
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

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
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

    public String getDescMd() {
        return descMd;
    }

    public void setDescMd(String descMd) {
        this.descMd = descMd;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getRealSuperChapterId() {
        return realSuperChapterId;
    }

    public void setRealSuperChapterId(int realSuperChapterId) {
        this.realSuperChapterId = realSuperChapterId;
    }

    public int getSelfVisible() {
        return selfVisible;
    }

    public void setSelfVisible(int selfVisible) {
        this.selfVisible = selfVisible;
    }

    public long getShareDate() {
        return shareDate;
    }

    public void setShareDate(long shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public List<TagsDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagsDTO> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static class TagsDTO implements Serializable {
        /**
         * name : 本站发布
         * url : /article/list/0?cid=440
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }





}
