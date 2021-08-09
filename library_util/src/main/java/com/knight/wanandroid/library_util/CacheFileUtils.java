package com.knight.wanandroid.library_util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/9 17:48
 * @descript:
 */
public class CacheFileUtils {


    /**
     *
     * 获取缓存总大小
     * @param context
     * @return
     */
    public static String getToalCacheSize(Context context) {
        long cacheSize = getFoldSize(context.getCacheDir());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += getFoldSize(context.getExternalCacheDir());
        }
        return SystemUtils.formetFileSize(cacheSize);
    }


    /**
     *
     * 返回文件的大小
     * getCacheDir()方法用于获取/data/data//cache目录
     * getFilesDir()方法用于获取/data/data//files目录
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/目录
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * @param file
     * @return
     */
    private static Long getFoldSize(File file) {
        long size = 0L;
        try {
           File[] files =  file.listFiles();
           for (int i = 0; i < files.length; i++) {
                //如果下面还有文件
               if (files[i].isDirectory()) {
                   size += getFoldSize(files[i]);
               } else {
                   size += files[i].length();
               }
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }


    /**
     *
     * 清理缓存
     * @param context
     */
    public static void cleadAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     *
     * 删除目录
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir,children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
