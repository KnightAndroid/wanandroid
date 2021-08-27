package com.knight.wanandroid.module_web.module_enum;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/26 11:31
 * @descript:
 */
public enum WebViewHitResultEnum {
    UNKNOWN_TYPE(0),
    ANCHOR_TYPE(1),
    PHONE_TYPE(2),
    GEO_TYPE(3),
    EMAIL_TYPE(4),
    IMAGE_TYPE(5),
    IMAGE_ANCHOR_TYPE(6),
    SRC_ANCHOR_TYPE(7),
    SRC_IMAGE_ANCHOR_TYPE(8),
    EDIT_TEXT_TYPE(9);
    int value;
    WebViewHitResultEnum(int value){
        this.value = value;
    }


}
