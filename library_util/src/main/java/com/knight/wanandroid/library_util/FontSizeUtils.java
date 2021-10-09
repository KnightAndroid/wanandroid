package com.knight.wanandroid.library_util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * @author created by luguian
 * @organize wanandroid
 * @Date 2021/8/23 13:44
 * @descript:字体大小工具类
 */
public final class FontSizeUtils {
    /**
     * 保持字体大小不随系统设置变化（用在界面加载之前）
     * 要重写Activity的attachBaseContext()
     */
    public static Context attachBaseContext(Context context, float fontScale) {
        Configuration config = context.getResources().getConfiguration();
        config.fontScale = fontScale;
        return context.createConfigurationContext(config);
    }









}
