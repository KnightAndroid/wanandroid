package com.knight.wanandroid.library_aop.clickintercept;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/4 17:38
 * @descript:双击处理类
 */
public class NoDoubbleClickUtils {

    private final static int FILTER_TIMEM = 500;//2次点击的间隔时间，单位ms
    private static long lastClickTime;


    public synchronized static boolean isDoubleClick(){
        long currentTime = System.currentTimeMillis();
        boolean isClick;

        if(currentTime - lastClickTime > FILTER_TIMEM){
            isClick = false;
        } else {
            isClick = true;
        }

        lastClickTime = currentTime;
        return isClick;

    }

}
