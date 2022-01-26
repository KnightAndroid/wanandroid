package com.knight.wanandroid.library_util;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Author:Knight
 * Time:2022/1/25 16:30
 * Description:BitmapUtils
 */
public class BitmapUtils {

    /**
     * 不考虑缩放，只考虑旋转角度的情况下，读取图片
     */
    public static Bitmap readBitmap(String imagePath) {
        return readBitmap(imagePath, null);
    }

    /**
     * 不考虑缩放，只考虑旋转角度的情况下，读取图片
     */
    public static Bitmap readBitmap(String imagePath, BitmapFactory.Options options) {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagePath);
            bitmap = BitmapFactory.decodeStream(fis, null, options);
        } catch (FileNotFoundException e) {

        } catch (OutOfMemoryError e) {

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {

                }
            }
        }
        return bitmap;
    }

    /**
     * 读取图片资源
     */
    public static Bitmap readBitmap(Resources res, int resID) {
        return readBitmap(res, resID, null);
    }

    /**
     * 读取图片资源
     *
     * @param res
     * @param resID
     * @param options
     * @return
     */
    public static Bitmap readBitmap(Resources res, int resID, BitmapFactory.Options options) {
        if (res == null) {
            return null;
        }
        try {
            return BitmapFactory.decodeResource(res, resID, options);
        } catch (OutOfMemoryError e) {

            System.gc();
            return null;
        } catch (Exception e) {

            return null;
        }
    }







}
