package com.knight.wanandroid.library_util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.knight.wanandroid.library_common.utils.CacheUtils;

import java.util.Locale;

import androidx.annotation.RequiresApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/10 18:50
 * @descript:
 */
public final class LanguageFontSizeUtils {


    public static Context attachBaseContext(Context context,float fontSize) {
        // 8.0需要使用createConfigurationContext处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context,fontSize);
        } else {
            return context;
        }
    }

    /**
     *
     * 低于8.0以下改变app内语言
     * @param context
     */
    public static void setAppLanguage(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getSetLanguageLocale();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
            context.createConfigurationContext(configuration);
        } else {
            configuration.locale = locale;
            resources.updateConfiguration(configuration, displayMetrics);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Context updateResources(Context context,float fontSize) {
        Resources resources = context.getResources();
        Locale locale = getSetLanguageLocale();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = fontSize;
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    /**
     * 获取选择的语言设置
     *
     *
     * @return
     */
    public static Locale getSetLanguageLocale() {
        switch (CacheUtils.getInstance().getLanguageMode()) {
            case "Auto":
                return getSystemLocale();
            case "简体中文":
                return Locale.SIMPLIFIED_CHINESE;
            case "English":
                return Locale.ENGLISH;
            default://默认 汉语
                return Locale.SIMPLIFIED_CHINESE;
        }
    }


    /**
     * 获取系统local
     *
     * @return
     */
    public static Locale getSystemLocale() {
        //获取系统默认语言,兼容版本
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        //暂时只处理英文 如果系统语言设置英文以外 默认为英文
        if (locale.getLanguage().equals(Locale.SIMPLIFIED_CHINESE.getLanguage())) {
            locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            locale = Locale.ENGLISH;
        }
        return locale;
    }




    /**
     *
     * 判断是否是中文
     * @return
     */
    public static boolean isChinese() {
        Locale locale = getSetLanguageLocale();
        if (locale.getLanguage().equals(Locale.SIMPLIFIED_CHINESE.getLanguage())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保持字体大小不随系统设置变化（用在界面加载之前）
     * 要重写Activity的getResources()
     */
    public static Resources getResources(Context context, Resources resources, float fontScale) {
        Configuration config = resources.getConfiguration();
        if(config.fontScale != fontScale) {
            config.fontScale = fontScale;
          //  resources.getDisplayMetrics().scaledDensity = resources.getDisplayMetrics().scaledDensity* fontScale;
            return context.createConfigurationContext(config).getResources();
        } else {
            return resources;
        }
    }


}
