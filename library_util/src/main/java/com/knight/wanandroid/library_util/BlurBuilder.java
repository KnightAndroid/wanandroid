package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/21 15:06
 * @descript:
 */
public class BlurBuilder {
    private static final float BITMAP_SCALE = 0.6f;
    private static final float BLUR_RADIUS = 25f;

    private static Bitmap tab_bg = null;
    private static Bitmap overlay = null;
    private static boolean blurFlag = false;

    public static boolean isBlurFlag() {
        return BlurBuilder.blurFlag;
    }

    public static void setBlurFlag(boolean blurFlag) {
        BlurBuilder.blurFlag = blurFlag;
    }

    public static Bitmap blur(View v) {
        if (tab_bg == null) {
            android.util.Log.i("", "tab_bg == null");
            blurFlag = false;
            return null;
        }
        blurFlag = true;
        blur(v.getContext(), tab_bg);
        return overlay;
    }

    public static void blur(Context ctx, Bitmap image) {
        if (overlay != null) {
            recycle();
        }
        try {
            int width = Math.round(image.getWidth() * BITMAP_SCALE);
            int height = Math.round(image.getHeight() * BITMAP_SCALE);

            overlay = Bitmap.createScaledBitmap(image, (int) (width),
                    (int) (height), false);

            overlay = FastBlurUtils.doBlur(overlay, (int) BLUR_RADIUS, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getScreenshot(View v) {
        if (tab_bg != null) {
            recycle();
        }
        try {
            tab_bg = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                    Bitmap.Config.RGB_565);
            Canvas c = new Canvas(tab_bg);
            v.draw(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static void snapShotWithoutStatusBar(Activity activity) {
        if (tab_bg != null) {
            recycle();
        }
        View view = activity.getWindow().getDecorView();
        try {
            view.buildDrawingCache();
            tab_bg = view.getDrawingCache();
            Rect frame = new Rect();
            activity.getWindow().getDecorView()
                    .getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            int width = ScreenUtils.getScreenWidth(activity);
            int height = ScreenUtils.getScreenHeight(activity);
            tab_bg = Bitmap.createBitmap(tab_bg, 0, statusBarHeight, width,
                    height - statusBarHeight);
            view.destroyDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
            getScreenshot(view);
        }
    }

    public static void recycle() {

        try {
            if (tab_bg != null) {
                tab_bg.recycle();
                System.gc();
                tab_bg = null;
            }
            if (overlay != null) {
                overlay.recycle();
                System.gc();
                overlay = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

