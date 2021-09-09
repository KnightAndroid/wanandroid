package com.knight.wanandroid.library_util.imageengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 15:03
 * @descript:图片加载方式
 */
public interface ImageLoaderProxy {


    /**
     *
     * 加载项目内的资源文件
     * @param context 上下文
     * @param resourceId 资源路径
     * @param imageView
     */
    void loadLocalPhoto(@NonNull Context context, @RawRes @DrawableRes @Nullable Integer resourceId, @NonNull ImageView imageView);

    /**
     *
     * 通过Uri方式加载图片到ImageView
     * @param context
     * @param uri
     * @param imageView
     */
    void loadUriPhoto(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView);

    /**
     *
     * 通过String方式加载图片到ImageView
     * @param context
     * @param uri
     * @param imageView
     */
    void loadStringPhoto(@NonNull Context context, @NonNull String uri, @NonNull ImageView imageView);

    /**
     * 加载String方式的圆形图片
     * @param context
     * @param uri
     * @param imageView
     */
    void loadCirCleStringPhoto(@NonNull Context context, @NonNull String uri, @NonNull ImageView imageView);

    /**
     *
     * 加载Int方式圆形图片
     * @param context
     * @param resourceId
     * @param imageView
     */
    void loadCircleIntLocalPhoto(@NonNull Context context,@RawRes @DrawableRes @Nullable Integer resourceId,@NonNull ImageView imageView);


    /**
     * 通过uri方式加载gif图片到ImageView
     * @param context
     * @param gifUri
     * @param imageView
     */
    void loadGifAsBitmap(@NonNull Context context,@NonNull Uri gifUri,@NonNull ImageView imageView);

    /**
     *
     * 通过Uri方式加载gif动图到ImageView
     * @param context
     * @param gifUri
     * @param imageView
     */
    void loadGif(@NonNull Context context,@NonNull Uri gifUri,@NonNull ImageView imageView);


    /**
     *
     * 获取图片加载框架中的缓存bitmap
     * @return
     */
    Bitmap getCacheBitmap(@NonNull Context context,@NonNull Uri uri,int width,int height) throws Exception;

}
