package com.knight.wanandroid.module_mine.entity;

import com.knight.wanandroid.library_base.entity.BaseEntity;

/**
 * Author:Knight
 * Time:2022/5/11 17:10
 * Description:UserInfoMessageEntity
 */
public class UserInfoMessageEntity extends BaseEntity {
    private UserInfoCoinEntity coinInfo;
    private CollectArticleInfo collectArticleInfo;

    public UserInfoCoinEntity getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(UserInfoCoinEntity coinInfo) {
        this.coinInfo = coinInfo;
    }

    public CollectArticleInfo getCollectArticleInfo() {
        return collectArticleInfo;
    }

    public void setCollectArticleInfo(CollectArticleInfo collectArticleInfo) {
        this.collectArticleInfo = collectArticleInfo;
    }

    public class CollectArticleInfo extends BaseEntity {
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
