package com.knight.wanandroid.library_common.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.knight.wanandroid.library_common.constant.MMkvConstants;
import com.tencent.mmkv.MMKV;

import java.lang.reflect.Type;



/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/16 14:39
 * @descript:缓存工具mmkv
 */
public final class CacheUtils {

    private static MMKV mmkv;

    /**
     *
     * 应用启动初始化
     * @param context
     */
    public static void init(Context context) {
        MMKV.initialize(context);
        mmkv = MMKV.defaultMMKV();
    }

    private CacheUtils() {

    }

    /**
     * 保存对象信息
     */
    public static <T> void saveDataInfo(String tag, T data) {
        if (data == null) {
            return;
        }
        mmkv.encode(tag, new Gson().toJson(data));
    }


    /**
     * 获取对象信息
     *
     * @param tag
     * @param <T>
     * @return
     */
    public static <T> T getDataInfo(String tag, Type typeOfT) {
        String userStr = mmkv.decodeString(tag);
        return new Gson().fromJson(userStr, typeOfT);
    }


    /**
     * 是否点击协议
     *
     * @param isAgree
     */
    public static void saveIsAgreeMent(boolean isAgree) {
        mmkv.encode(MMkvConstants.AGREEMENT, isAgree);

    }

    /**
     * 返回是否同意协议状态
     *
     * @return
     */
    public static boolean getAgreeStatus() {
        return mmkv.decodeBool(MMkvConstants.AGREEMENT, false);
    }


    /**
     * 清空用户信息
     */
    public static void loginOut() {
        mmkv.remove(MMkvConstants.USER);
    }


    /**
     * 设置是否跟随系统
     *
     * @param isFollowSystem
     * @return
     */
    public static void setFollowSystem(boolean isFollowSystem) {
        mmkv.encode(MMkvConstants.ISFOLLOWSYSTEM, isFollowSystem);
    }

    /**
     * 返回是否跟随系统 默认不跟随系统
     *
     * @return
     */
    public static boolean getFollowSystem() {
        return mmkv.decodeBool(MMkvConstants.ISFOLLOWSYSTEM, false);
    }


    /**
     * 设置普通模式还是深色模式
     */
    public static void setNormalDark(boolean normalDark) {
        mmkv.encode(MMkvConstants.NORMALDARK, normalDark);
    }

    /**
     * 获取模式是普通还是深色模式
     * 默认返回普通
     *
     * @return true 深色模式 false  不是深色模式
     */
    public static boolean getNormalDark() {
        return mmkv.decodeBool(MMkvConstants.NORMALDARK, false);
    }


    /**
     * 状态栏是否伴随主题着色
     *
     * @param withTheme
     */
    public static void statusBarIsWithTheme(boolean withTheme) {
        mmkv.encode(MMkvConstants.STATUSWITHTHEME, withTheme);

    }


    /**
     * 获取状态栏是否伴随着色
     *
     * @return
     */
    public static boolean getStatusBarIsWithTheme() {
        return mmkv.decodeBool(MMkvConstants.STATUSWITHTHEME, false);
    }


    /**
     * 设置主题颜色
     *
     * @param themecolor
     */
    public static void setThemeColor(int themecolor) {
        mmkv.encode(MMkvConstants.THEMECOLOR, themecolor);
    }


    /**
     * 返回主题颜色
     *
     * @return
     */
    public static int getThemeColor() {
        return mmkv.decodeInt(MMkvConstants.THEMECOLOR, ColorUtils.convertToColorInt("55aff4"));
    }


    /**
     * 设置是否护眼
     */
    public static void setIsEyeCare(boolean isEyeCare) {
        mmkv.encode(MMkvConstants.EYECARE, isEyeCare);
    }


    /**
     * 返回是否护眼模式
     *
     * @return
     */
    public static boolean getIsEyeCare() {
        return mmkv.decodeBool(MMkvConstants.EYECARE, false);
    }


    /**
     * 返回是什么语言模式 跟随系统 中文 英文
     */
    public static String getLanguageMode() {
        return mmkv.decodeString(MMkvConstants.LANGUAGE, "简体中文");
    }


    /**
     * 保存语言模式
     *
     * @param languageType
     */
    public static void setLanguageType(String languageType) {
        mmkv.encode(MMkvConstants.LANGUAGE, languageType);
    }


    /**
     *
     * 字体缩放系数
     * @param fontSizeScale
     */
    public static void setSystemFontSize(float fontSizeScale) {
        mmkv.encode(MMkvConstants.FONTSIZESCALE,fontSizeScale);
    }


    /**
     *
     * 返回字体缩放系数
     * @return
     */
    public static float getSystemFontSize() {
        return mmkv.decodeFloat(MMkvConstants.FONTSIZESCALE,1.0f);
    }


    /**
     *
     * 保存网络缓存
     * @param cacheKey
     * @param cacheValue
     */
    public static boolean saveCacheValue(String cacheKey,String cacheValue) {
        return mmkv.putString(cacheKey,cacheValue).commit();
    }


    /**
     *
     * 取出缓存值
     * @param cacheKey
     * @return
     */
    public static String getCacheValue(String cacheKey) {
        return mmkv.getString(cacheKey,null);
    }

    /**
     * 返回生物识别iv向量
     */
    public static String getCliperIv() {
        return mmkv.decodeString(MMkvConstants.CLIPER_IV, "");
    }


    /**
     * 保存生物识别iv向量
     *
     * @param cliperIv
     */
    public static void setCliperIv(String cliperIv) {
        mmkv.encode(MMkvConstants.CLIPER_IV, cliperIv);
    }

    /**
     * 返回Base64后的登录账号信息
     */
    public static String getEncryptLoginMessage() {
        return mmkv.decodeString(MMkvConstants.ENCRYPT_LOGIN_MESSAGE, "");
    }


    /**
     * 保存Base64后的登录账号信息
     *
     * @param encryptloginMessage
     */
    public static void setEncryptLoginMessage(String encryptloginMessage) {
        mmkv.encode(MMkvConstants.ENCRYPT_LOGIN_MESSAGE, encryptloginMessage);
    }


    /**
     *
     * 保存明文账号密码信息
     * @return
     */
    public static void setLoginMessage(String loginMessage) {
        mmkv.encode(MMkvConstants.LOGIN_MESSAGE,loginMessage);
    }


    /**
     * 得到明文账号密码信息
     * @return
     */
    public static String getLoginMessage() {
        return mmkv.decodeString(MMkvConstants.LOGIN_MESSAGE,"");
    }



    /**
     * 设置是否指纹登录
     */
    public static void setFingerLogin(boolean isQuickLogin) {
        mmkv.encode(MMkvConstants.FINGERLOGIN, isQuickLogin);
    }



    /**
     * 返回是否开启指纹登录
     * @return
     */
    public static boolean getFingerLogin() {
        return mmkv.decodeBool(MMkvConstants.FINGERLOGIN, false);
    }

    /**
     * 存放手势密码
     *
     */
    public static void setGesturePassword(String gesturePassword) {
        mmkv.encode(MMkvConstants.GESTUREPASSEORD,gesturePassword);
    }

    /**
     *
     * 返回手势密码 是否已经创建手势密码
     * @return
     */
    public static String getGesturePassword() {
        return mmkv.decodeString(MMkvConstants.GESTUREPASSEORD);
    }


    /**
     *
     *  返回是否开启手势密码登录
      * @return
     */
    public static boolean getGestureLogin() {
        return mmkv.decodeBool(MMkvConstants.GESTURELOGIN,false);
    }

    /**
     *
     * 设置手势密码登录
     * @param gestureLogin
     */
    public static void setGestureLogin(boolean gestureLogin) {
        mmkv.encode(MMkvConstants.GESTURELOGIN, gestureLogin);
    }


    /**
     *
     * 获取夜间模式开启时间 时
     * @return
     */
    public static String getStartNightModeHour() {
        if (TextUtils.isEmpty(mmkv.decodeString(MMkvConstants.StARTNIGHTHOUR))) {
          return "22";
        } else {
          return mmkv.decodeString(MMkvConstants.StARTNIGHTHOUR);
        }
    }


    /**
     *
     * 设置夜间模式开启时间 时
     * @return
     */
    public static void setStartNightModeHour(String hour) {
         mmkv.encode(MMkvConstants.StARTNIGHTHOUR,hour);
    }

    /**
     *
     * 获取夜间模式开启时间 分
     * @return
     */
    public static String getStartNightModeMinuter() {
        if (TextUtils.isEmpty(mmkv.decodeString(MMkvConstants.StARTNIGHTMINUTER))) {
          return "00";
        } else {
            return mmkv.decodeString(MMkvConstants.StARTNIGHTMINUTER);
        }

    }


    /**
     *
     * 设置夜间模式开启时间 分
     * @return
     */
    public static void setStartNightModeMinuter(String minuter) {
        mmkv.encode(MMkvConstants.StARTNIGHTMINUTER,minuter);
    }


    /**
     *
     * 获取白天模式开启时间 时
     * @return
     */
    public static String getStartDayModeHour() {
        if (TextUtils.isEmpty(mmkv.decodeString(MMkvConstants.StARTDAYHOUR))) {
            return "06";
        } else {
            return mmkv.decodeString(MMkvConstants.StARTDAYHOUR);
        }
    }


    /**
     *
     * 设置白天模式开启时间 时
     * @return
     */
    public static void setStartDayModeHour(String hour) {
        mmkv.encode(MMkvConstants.StARTDAYHOUR,hour);
    }

    /**
     *
     * 获取白天模式开启时间 分
     * @return
     */
    public static String getStartDayModeMinuter() {
        if (TextUtils.isEmpty(mmkv.decodeString(MMkvConstants.StARTDAYMINUTER))) {
            return  "00";
        } else {
            return mmkv.decodeString(MMkvConstants.StARTDAYMINUTER);
        }
    }


    /**
     *
     * 设置白天模式开启时间 分
     * @return
     */
    public static void setStartDayModeMinuter(String minuter) {
        mmkv.encode(MMkvConstants.StARTDAYMINUTER,minuter);
    }

    /**
     *
     * 设置开启夜间模式
     * @param nightMode
     */
    public static void setOpenAutoNightMode(boolean nightMode) {
        mmkv.encode(MMkvConstants.AUTONIGHTMODE,nightMode);

    }


    /**
     *
     * 返回夜间模式开启状态
     * @return
     */
    public static boolean getAutoNightMode() {
        return mmkv.decodeBool(MMkvConstants.AUTONIGHTMODE,false);
    }


    /**
     *
     * 设置进入APP时是否夜间状态
     *
     */
    public static void setNightModeStatus(boolean nightMode) {
        mmkv.encode(MMkvConstants.INNIGHTMODE,nightMode);
    }


    /**
     *
     * 返回当前是否夜间模式
     * @return
     */
    public static boolean getNightModeStatus() {
        return mmkv.decodeBool(MMkvConstants.INNIGHTMODE);
    }





}
