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

    /**
     *
     * 判断时间是否是今天
     * @param time
     * @return
     */
    public static boolean isToday(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e){
            e.printStackTrace();
            return false;
        }

        Calendar c1 = Calendar.getInstance();

        c1.setTime(date);

        int year1 = c1.get(Calendar.YEAR);

        int month1 = c1.get(Calendar.MONTH)+1;

        int day1 = c1.get(Calendar.DAY_OF_MONTH);

        Calendar c2 = Calendar.getInstance();

        c2.setTime(new Date());

        int year2 = c2.get(Calendar.YEAR);

        int month2 = c2.get(Calendar.MONTH)+1;

        int day2 = c2.get(Calendar.DAY_OF_MONTH);

        if(year1 == year2 && month1 == month2 && day1 == day2){
            return true;

        }

        return false;
    }
}
