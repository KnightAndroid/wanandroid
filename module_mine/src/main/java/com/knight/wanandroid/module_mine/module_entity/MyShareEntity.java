package com.knight.wanandroid.module_mine.module_entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/8 13:58
 * @descript:
 */
public final class MyShareEntity extends BaseEntity {


    private UserInfoEntity coinInfo;

    private MyArticleListEntity shareArticles;

    public UserInfoEntity getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(UserInfoEntity coinInfo) {
        this.coinInfo = coinInfo;
    }

    public MyArticleListEntity getShareArticles() {
        return shareArticles;
    }

    public void setShareArticles(MyArticleListEntity shareArticles) {
        this.shareArticles = shareArticles;
    }
}
