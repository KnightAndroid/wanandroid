package com.knight.wanandroid.library_util.imageengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 14:58
 * @descript:图像加载实现
 */
public class DefaultImageLoaderProxy implements ImageLoaderProxy {




    /**
     *
     * 加载项目里的框架
     * @param context 上下文
     * @param resourceId 资源路径
     * @param imageView
     */
    @Override
    public void loadLocalPhoto(@NonNull Context context, @Nullable Integer resourceId, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .transition(withCrossFade())
                .into(imageView);
    }

    /**
     *
     * 通过uri加载到ImageView
     * @param context
     * @param uri
     * @param imageView
     */
    @Override
    public void loadUriPhoto(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView) {
        Glide.with(context).load(uri).transition(withCrossFade()).into(imageView);
    }

    @Override
    public void loadStringPhoto(@NonNull Context context, @NonNull String uri, @NonNull ImageView imageView) {
        Glide.with(context).load(uri).transition(withCrossFade()).into(imageView);
    }

    @Override
    public void loadCirCleStringPhoto(@NonNull Context context, @NonNull String uri, @NonNull ImageView imageView) {
        Glide.with(context).load(uri).apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .override(imageView.getWidth(),imageView.getHeight())
                        ).into(imageView);
    }

    @Override
    public void loadCircleIntLocalPhoto(@NonNull Context context, @Nullable Integer resourceId, @NonNull ImageView imageView) {
        Glide.with(context).load(resourceId).apply(RequestOptions.bitmapTransform(new CircleCrop())
                .override(imageView.getWidth(),imageView.getHeight())
        ).into(imageView);
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
     * 加载gif 暂时先这样处理
     * @param context
     * @param resourceId
     * @param imageView
     */
    @Override
    public void loadGif(@NonNull Context context, @Nullable Integer resourceId, @NonNull ImageView imageView) {
        Glide.with(context).asGif().load(resourceId).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                //设置循环次数
                resource.setLoopCount(2);
                resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationStart(Drawable drawable) {
                        super.onAnimationStart(drawable);
                        //播放开始
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        super.onAnimationEnd(drawable);
                        //播放完成
                        imageView.setVisibility(View.GONE);
                    }
                });
                return false;
            }
        }).transition(withCrossFade()).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
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
