package com.knight.wanandroid.library_util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/11 16:08
 * @descript:
 */
public final class ThreadPoolUtils {
    private static ExecutorService pool = Executors.newCachedThreadPool();
    private static ExecutorService singlePool = Executors.newSingleThreadExecutor();


    public static void execute(Runnable runnable){
        pool.execute(runnable);
    }

    public static void singleExecute(Runnable runnable){
        singlePool.execute(runnable);
    }



}
