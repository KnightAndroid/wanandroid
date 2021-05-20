package com.knight.wanandroid.module_mine.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 19:35
 * @descript:
 */
public class OtherShareArticleListEntity extends BaseEntity {

   private UserMessageEntity coinInfo;
   private MyArticleListEntity shareArticles;

    public UserMessageEntity getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(UserMessageEntity coinInfo) {
        this.coinInfo = coinInfo;
    }

    public MyArticleListEntity getShareArticles() {
        return shareArticles;
    }

    public void setShareArticles(MyArticleListEntity shareArticles) {
        this.shareArticles = shareArticles;
    }
}
