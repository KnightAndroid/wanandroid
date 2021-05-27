package com.knight.wanandroid.library_util;

import com.google.gson.Gson;
import com.knight.wanandroid.library_util.constant.MMkvConstants;
import com.tencent.mmkv.MMKV;

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
    public <T> T getDataInfo(String tag,Class<T> cls){
         String userStr = mmkv.decodeString(tag);
         return new Gson().fromJson(userStr,cls);
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

}
