package com.knight.wanandroid.library_util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 10:17
 * @descript:
 */
public class GsonUtils {
    /**
     * 转换Json对象到对象（非泛型，非List）
     * @param jsonObject
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T get(JSONObject jsonObject, Class<T> cls){
        return get(jsonObject.toString(), cls);
    }

    /**
     * 转换Json字符串到对象（非泛型，非List）
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T get(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            LogUtils.debugInfo("解析数据异常");
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 转换Json字符串到对象（非泛型，非List）
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getToSpecial(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(new Gson().toJson(jsonString), cls);
        } catch (Exception e) {
            LogUtils.debugInfo("解析数据异常");
            e.printStackTrace();
        }
        return t;
    }
    /**
     * 转换Json字符串到对象 由调用者处理异常
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T get(String jsonString, Class<T> cls, int type) throws Exception {
        T t = null;
        Gson gson = new Gson();
        t = gson.fromJson(jsonString, cls);
        return t;
    }

    /**
     * 转换Json字符串到对象（非List）
     * 当转换的对象为复杂泛型类型时，可以使用此接口：
     * Type type = new TypeToken<ReturnData<Business>>(){}.getType();
     * @param jsonString
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T get(String jsonString, Type type){
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, type);
        } catch (Exception e) {

        }
        return t;
    }

    /**
     * 转换JSONObject到对象（非List）
     * 当转换的对象为复杂泛型类型时，可以使用此接口：
     * Type type = new TypeToken<ReturnData<Business>>(){}.getType();
     * @param jsonObject
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T get(JSONObject jsonObject, Type type){
        return get(jsonObject.toString(), type);
    }

    /**
     * 转换Json字符串到列表(Json字符串内容必须是数组）
     * @param jsonString
     * @param type  Type type = new TypeToken<ArrayList<T>>() {}.getType();
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> getList(String jsonString, Type type) {

        ArrayList<T> list = new ArrayList<T>();
        try {
            //  list = JSON.parseObject(jsonString, type);

            //  Gson gson = GsonAdapter.buildGson();

            list = new Gson().fromJson(jsonString, type);
        } catch (Exception e) {
            LogUtils.debugInfo(e.getMessage());
            LogUtils.debugInfo("解析数据异常");
            e.printStackTrace();
        }
        return list;
    }



    public static <T> List<T> getObjectList(String jsonString,Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 转换Json字符串到列表(Json字符串内容必须是数组）
     * @param jsonString
     * @return
     */
    public static List getList(String jsonString){
        TypeToken<ArrayList> typeToken = new TypeToken<ArrayList>(){};
        List list = new ArrayList();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, typeToken.getType());
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * 转换JSONArray到列表
     * @param array
     * @return
     */
    public static List getList(JSONArray array){
        return getList(array.toString());
    }

    /**
     * 对象转换为Json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(obj);
    }

    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        //add: 20160420
        if(!jsonObject.has(key)) {
            return defaultValue;
        }

        return jsonObject.optString(key);

    }


    /**
     *
     *
     * 根据对应json key 值转为字符串
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     */
    public static String jsonToString(String jsonObject,String key, String defaultValue){
        try {
            return getString(new JSONObject(jsonObject),key,defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";

    }


    /**
     *
     *
     * 根据json 解析出对应model
     * @param json
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToModel(String json, String key, Class<T> clazz) {
        return get(jsonToString(json, key, ""), clazz);
    }
}
