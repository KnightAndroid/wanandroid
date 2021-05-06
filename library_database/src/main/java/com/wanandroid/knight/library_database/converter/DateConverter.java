package com.wanandroid.knight.library_database.converter;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 14:21
 * @descript:时间转换
 */
public class DateConverter {

    @TypeConverter
    public static Date revertDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long converterDate(Date value) {
        if (value == null) {
            value = new Date();
        }
        return value.getTime();
    }
}