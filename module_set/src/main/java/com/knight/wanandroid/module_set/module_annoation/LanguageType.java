package com.knight.wanandroid.module_set.module_annoation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/10 15:40
 * @descript:语言类型
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({LanguageType.FollowSystem,LanguageType.Chinese,LanguageType.English})
public @interface LanguageType {
    String FollowSystem = "FollowSystem";
    String Chinese = "Chinese";
    String English = "English";
}
