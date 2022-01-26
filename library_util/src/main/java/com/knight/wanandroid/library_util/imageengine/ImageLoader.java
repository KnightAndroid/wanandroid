package com.knight.wanandroid.library_util.imageengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/9 15:02
 * @descript:
 */
public class ImageLoader {


    private static ImageLoaderProxy mImageLoaderProxy = new DefaultImageLoaderProxy();

    private ImageLoader() {

    }

    public static ImageLoaderProxy getInstance() {
        return mImageLoaderProxy;
    }

    /**
     *
     * 加载项目内的资源文件
     * @param context 上下文
     * @param resourceId 资源路径
     * @param imageView
     */
    public static void loadLocalPhoto(@NonNull Context context, @Nullable Integer resourceId, @NonNull ImageView imageView) {
        mImageLoaderProxy.loadLocalPhoto(context, resourceId, imageView);
    }

    /**
     * 通过uri加载到ImageView
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static void loadUriPhoto(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView) {
        mImageLoaderProxy.loadUriPhoto(context, uri, imageView);
    }

    /**
     *
     * 通过String方式加载图片到ImageView
     * @param context
     * @param uri
     * @param imageView
     */
    public static void loadStringPhoto(@NonNull Context context, @NonNull String uri, @NonNull ImageView imageView) {
        mImageLoaderProxy.loadStringPhoto(context, uri, imageView);
    }

    /**
     * 加载String方式的圆形图片
     * @param context
     * @param uri
     * @param imageView
     */
    public static void loadCirCleStringPhoto(@NonNull Context context, @NonNull String uri, @NonNull ImageView imageView) {
        mImageLoaderProxy.loadCirCleStringPhoto(context, uri, imageView);
    }

    /**
     *
     * 加载Int方式圆形图片
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void loadCircleIntLocalPhoto(@NonNull Context context, @Nullable Integer resourceId, @NonNull ImageView imageView) {
        mImageLoaderProxy.loadCircleIntLocalPhoto(context, resourceId, imageView);
    }

    /**
     * 通过gif转为bitmap加载到imageView
     *
     * @param context
     * @param gifUri
     * @param imageView
     */

    public static void loadGifAsBitmap(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        mImageLoaderProxy.loadGifAsBitmap(context, gifUri, imageView);
    }

    /**
     * 通过Uri方式加载gif
     *
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void loadGif(@NonNull Context context, @Nullable Integer resourceId, @NonNull ImageView imageView) {
        mImageLoaderProxy.loadGif(context, resourceId, imageView);
    }

    /**
     * 获取图片加载框架中的缓存bitmap
     *
     * @param context
     * @param uri
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    public static Bitmap getCacheBitmap(@NonNull Context context, @NonNull Uri uri, int width, int height) throws Exception {
        return mImageLoaderProxy.getCacheBitmap(context, uri, width, height);
    }
}
