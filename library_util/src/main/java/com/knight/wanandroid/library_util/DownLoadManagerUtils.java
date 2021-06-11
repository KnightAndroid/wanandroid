package com.knight.wanandroid.library_util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/11 15:22
 * @descript:
 */
public class DownLoadManagerUtils {


    /**
     *
     * 下载文件
     * @param path
     * @param pb
     * @param mHandler
     * @param context
     * @return
     */
     public static File downloadApk(String path, ProgressBar pb, Handler mHandler, Context context){
         File apkFile = context.getExternalFilesDir("Wanandroid_APK");
        if (!apkFile.exists()) {
            apkFile.mkdir();
        }

        File file = new File(apkFile.getAbsolutePath() + File.separator + getApkName());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         try {
             URL url = new URL(path);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setConnectTimeout(5000);
             conn.setRequestMethod("GET");
             if (conn.getResponseCode() == 200) {
                 long max = conn.getContentLength();
                 pb.setMax((int)max);
                 int count = 0;
                 InputStream inputStream = conn.getInputStream();
                 //保存文件
                 FileOutputStream fos = new FileOutputStream(file);
                 byte[] buffer = new byte[1024];
                 while (true) {
                     int len = inputStream.read(buffer);
                     if (len == -1) {
                         inputStream.close();
                         fos.close();
                         break;
                     }

                     fos.write(buffer,0,len);
                     count += len;
                     pb.setProgress(count);
                     Message message = new Message();
                     message.what = 2;
                     message.arg1 = pb.getMax();
                     message.arg2 = count;
                     mHandler.sendMessage(message);
                 }

             }

         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return file;

     }



     private static String getApkName(){
         return "wanandroid.apk";

     }






}
