package com.knight.wanandroid.library_network.json;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:23
 * @descript float / Float 类型解析适配器
 */
public class FloatTypeAdapter extends DoubleTypeAdapter {

    @Override
    public Number read(JsonReader in) throws IOException {
        Number number = super.read(in);
        if (number != null) {
            return number.floatValue();
        }
        return null;
    }
}