package com.knight.wanandroid.library_util;

import com.google.gson.Gson;
import com.knight.wanandroid.library_util.constant.MMkvConstants;
import com.tencent.mmkv.MMKV;

import java.lang.reflect.Type;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/16 14:39
 * @descript:缓存工具mmkv
 */
public class CacheUtils {

    private static MMKV mmkv;

    private CacheUtils() {
        mmkv = MMKV.defaultMMKV();
    }

    public static CacheUtils getInstance() {
        return CacheHolder.INSTANC;
    }

    private static class CacheHolder {
        private static final CacheUtils INSTANC = new CacheUtils();
    }

    /**
     *
     * 保存对象信息
     */
    public <T> void saveDataInfo(String tag,T data){
        if (data == null) {
            return;
        }
        mmkv.encode(tag, new Gson().toJson(data));
    }


    /**
     * 获取对象信息
     * @param tag
     * @param <T>
     * @return
     */
    public <T> T getDataInfo(String tag, Type typeOfT){
        String userStr = mmkv.decodeString(tag);
        return new Gson().fromJson(userStr,typeOfT);
    }




    /**
     *
     * 是否点击协议
     * @param isAgree
     */
    public void saveIsAgreeMent(boolean isAgree){
        mmkv.encode(MMkvConstants.AGREEMENT,isAgree);

    }

    /**
     *
     * 返回是否同意协议状态
     * @return
     */
    public boolean getAgreeStatus(){
        return mmkv.decodeBool(MMkvConstants.AGREEMENT,false);
    }


    /**
     *
     * 清空用户信息
     */
    public void loginOut(){
        mmkv.remove(MMkvConstants.USER);
    }


    /**
     *
     * 设置是否跟随系统
     * @param isFollowSystem
     * @return
     */
    public void setFollowSystem(boolean isFollowSystem) {
        mmkv.encode(MMkvConstants.ISFOLLOWSYSTEM,isFollowSystem);
    }

    /**
     *
     * 返回是否跟随系统 默认不跟随系统
     * @return
     */
    public boolean getFollowSystem() {
        return mmkv.decodeBool(MMkvConstants.ISFOLLOWSYSTEM,false);
    }


    /**
     *
     * 设置普通模式还是深色模式
     */
    public void setNormalDark(boolean normalDark) {
        mmkv.encode(MMkvConstants.NORMALDARK,normalDark);
    }

    /**
     *
     * 获取模式是普通还是深色模式
     * 默认返回普通
     * @return true 深色模式 false  不是深色模式
     *
     */
    public boolean getNormalDark() {
        return mmkv.decodeBool(MMkvConstants.NORMALDARK,false);
    }


    /**
     *
     * 状态栏是否伴随主题着色
     * @param withTheme
     */
    public void statusBarIsWithTheme(boolean withTheme) {
        mmkv.encode(MMkvConstants.STATUSWITHTHEME,withTheme);

    }


    /**
     *
     * 获取状态栏是否伴随着色
     * @return
     */
    public boolean getStatusBarIsWithTheme(){
        return mmkv.decodeBool(MMkvConstants.STATUSWITHTHEME,false);
    }


    /**
     *
     *
     * 设置主题颜色
     * @param themecolor
     */
    public void setThemeColor(int themecolor){
       mmkv.encode(MMkvConstants.THEMECOLOR,themecolor);
    }


    /**
     *
     * 返回主题颜色
     * @return
     */
    public int getThemeColor(){
        return mmkv.decodeInt(MMkvConstants.THEMECOLOR,ColorUtils.convertToColorInt("55aff4"));
    }


    /**
     *
     *
     * 设置页面背景颜色
     * @param themecolor
     */
    public void setBgThemeColor(int themecolor){
        mmkv.encode(MMkvConstants.BGTHEMECOLOR,themecolor);
    }


    /**
     *
     * 返回页面背景颜色
     * @return
     */
    public int getBgThemeColor(){
        return mmkv.decodeInt(MMkvConstants.BGTHEMECOLOR,ColorUtils.convertToColorInt("f9f9f9"));
    }


    /**
     *
     * 设置是否护眼
     *
     */
    public void setIsEyeCare(boolean isEyeCare) {
        mmkv.encode(MMkvConstants.EYECARE,isEyeCare);
    }


    /**
     *
     * 返回是否护眼模式
     * @return
     */
    public boolean getIsEyeCare(){
        return mmkv.decodeBool(MMkvConstants.EYECARE,false);
    }




}
