package com.knight.wanandroid.library_util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 14:39
 * @descript:日期时间帮助类
 */
public class DateUtils {

    /**
     *
     * 时间戳转成yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String ConvertStringTime(long date){
        //要转换的时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTemp;
        try {
            dateTemp = sdf.parse(sdf.format(date));
            return sdf.format(dateTemp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }
}
