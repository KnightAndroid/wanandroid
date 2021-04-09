package com.knight.wanandroid.library_util.imageengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 14:58
 * @descript:图像加载实现
 */
public class GlideEngineUtils implements ImageEngine  {

    private static GlideEngineUtils instance = null;
    private GlideEngineUtils () {

    }

    public static GlideEngineUtils getInstance() {
        if (null == instance) {
            synchronized (GlideEngineUtils.class) {
                if (null == instance) {
                    instance = new GlideEngineUtils();
                }
            }
        }
        return instance;
    }

    /**
     *
     * 加载项目里的框架
     * @param context 上下文
     * @param resourceId 资源路径
     * @param imageView
     */
    @Override
    public void loadLocalPhoto(@NonNull Context context, @Nullable Integer resourceId, @NonNull ImageView imageView) {
        Glide.with(context).load(resourceId).transition(withCrossFade()).into(imageView);
    }

    /**
     *
     * 通过uri加载到ImageView
     * @param context
     * @param uri
     * @param imageView
     */
    @Override
    public void loadPhoto(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView) {
        Glide.with(context).load(uri).transition(withCrossFade()).into(imageView);
    }

    /**
     *
     * 通过gif转为bitmap加载到imageView
     * @param context
     * @param gifUri
     * @param imageView
     */
    @Override
    public void loadGifAsBitmap(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        Glide.with(context).asBitmap().load(gifUri).into(imageView);
    }

    /**
     *
     * 加载gif
     * @param context
     * @param gifUri
     * @param imageView
     */
    @Override
    public void loadGif(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        Glide.with(context).asGif().load(gifUri).transition(withCrossFade()).into(imageView);
    }

    /**
     *
     * 获取图片加载框架中的缓存bitmap
     * @param context
     * @param uri
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    @Override
    public Bitmap getCacheBitmap(@NonNull Context context, @NonNull Uri uri, int width, int height) throws Exception {
        return Glide.with(context).asBitmap().load(uri).submit(width, height).get();
    }
}
