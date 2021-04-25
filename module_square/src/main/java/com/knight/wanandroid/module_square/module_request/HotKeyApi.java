package com.knight.wanandroid.module_square.module_request;

import com.knight.wanandroid.library_network.config.IRequestApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/25 14:44
 * @descript:
 */
public class HotKeyApi implements IRequestApi {

    @Override
    public String getApi() {
        return "hotkey/json";
    }
}
