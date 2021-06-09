package com.knight.wanandroid.library_util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     *
     * 时间戳转成yyyy-MM-dd
     * @param date
     * @return
     */
    public static String ConvertYearMonthDayTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minuter = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + "-" +month+"-"+day+" "+hour+":"+minuter+":"+second;
    }
}
