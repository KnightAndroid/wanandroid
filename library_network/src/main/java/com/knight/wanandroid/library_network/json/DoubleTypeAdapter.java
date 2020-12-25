package com.knight.wanandroid.library_network.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:23
 * @descript double / Double 类型解析适配器
 */
public class DoubleTypeAdapter extends TypeAdapter<Number> {

    @Override
    public Number read(JsonReader in) throws IOException {
        switch (in.peek()) {
            case NUMBER:
                return in.nextDouble();
            case STRING:
                try {
                    return Double.parseDouble(in.nextString());
                } catch (NumberFormatException e) {
                    // 如果是空字符串则会抛出这个异常
                    return 0;
                }
            case NULL:
                in.nextNull();
                return null;
            default:
                in.skipValue();
                return 0;
        }
    }
    @Override
    public void write(JsonWriter out, Number value) throws IOException {
        out.value(value);
    }
}