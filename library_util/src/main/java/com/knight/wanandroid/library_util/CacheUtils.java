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
     */
    public boolean getNormalDark() {
        return mmkv.decodeBool(MMkvConstants.NORMALDARK,false);
    }







}
