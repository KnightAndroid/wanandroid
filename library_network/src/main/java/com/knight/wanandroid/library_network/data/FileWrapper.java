package com.knight.wanandroid.library_network.data;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.NetWorkUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;

/**
 * @author created by luguian
 * @organize wanandroid
 * @Date 2021/8/30 14:47
 * @descript:File  包装类
 */
public class FileWrapper extends File  {
    public FileWrapper(@NonNull File file) {
        super(file.getPath());
    }

    /**
     * 获取文件的输出流
     */
    public OutputStream getOutputStream() throws FileNotFoundException {
        return new FileOutputStream(this);
    }

    /**
     * 获取文件的输入流
     */
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(this);
    }

    /**
     * 创建文件夹
     */
    public static boolean createFolder(File targetFolder) {
        if (targetFolder.exists()) {
            if (targetFolder.isDirectory()) {
                return true;
            }
            // noinspection ResultOfMethodCallIgnored
            targetFolder.delete();
        }
        return targetFolder.mkdirs();
    }

    /**
     * 获取文件的 md5
     */
    public static String getFileMd5(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        DigestInputStream digestInputStream = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(inputStream, messageDigest);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) {
                    break;
                }
            }
            messageDigest = digestInputStream.getMessageDigest();
            byte[] md5 = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : md5) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException | IOException e) {
            EasyLog.print(e);
        } finally {
            NetWorkUtils.closeStream(digestInputStream);
        }
        return null;
    }
}
