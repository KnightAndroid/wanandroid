package com.knight.wanandroid.library_network.data;

import com.knight.wanandroid.library_network.HttpConfig;

import java.util.HashMap;
import java.util.Set;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:41
 * @descript:
 */
public final class HttpParams {

    /** 请求参数存放集合 */
    private HashMap<String, Object> mParams = HttpConfig.getInstance().getParams();

    /** 是否有流参数 */
    private boolean mMultipart;

    public void put(String key, Object value) {
        if (key != null && value != null) {
            if (mParams == HttpConfig.getInstance().getParams()) {
                mParams = new HashMap<>(mParams);
            }
            mParams.put(key, value);
        }
    }

    public void remove(String key) {
        if (key != null) {
            if (mParams == HttpConfig.getInstance().getParams()) {
                mParams = new HashMap<>(mParams);
            }
            mParams.remove(key);
        }
    }

    public Object get(String key) {
        return mParams.get(key);
    }

    public boolean isEmpty() {
        return mParams == null || mParams.isEmpty();
    }

    public Set<String> getNames() {
        return mParams.keySet();
    }

    public HashMap<String, Object> getParams() {
        return mParams;
    }

    public boolean isMultipart() {
        return mMultipart;
    }

    public void setMultipart(boolean multipart) {
        mMultipart = multipart;
    }
}