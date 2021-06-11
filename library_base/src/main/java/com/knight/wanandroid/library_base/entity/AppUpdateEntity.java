package com.knight.wanandroid.library_base.entity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/11 13:49
 * @descript:
 */
public class AppUpdateEntity extends BaseEntity {


    /**
     * downLoadLink : https://wanandroid.com/blogimgs/d4ff4c56-0129-40a1-851f-93e4135d39aa.apk
     * versionCode : 2
     * versionName : 1.0.1
     * forceUpdate : true
     * updateDesc : 1、新增阅读历史记录
     2、新增搜索公众号
     3、新增文章阅读侧滑返回
     * title : 1.0.1版本发布
     * updateTime : 2021-06-10
     */

    private String downLoadLink;
    private int versionCode;
    private String versionName;
    private boolean forceUpdate;
    private String updateDesc;
    private String title;
    private String updateTime;

    public String getDownLoadLink() {
        return downLoadLink;
    }

    public void setDownLoadLink(String downLoadLink) {
        this.downLoadLink = downLoadLink;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
