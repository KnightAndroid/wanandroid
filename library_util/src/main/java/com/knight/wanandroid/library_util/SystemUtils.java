package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DecimalFormat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/2 14:56
 * @descript:
 */
public class SystemUtils {

    /**
     *
     * 隐藏软键盘
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     *
     * 延迟显示软键盘
     * @param view
     */
    public static void showDelaySoftKeyBoard(View view){
        new Handler(Looper.getMainLooper()).postDelayed(()->{

            showSoftKeyBoard(view);
        },500);

    }


    /**
     *
     * 显示软键盘
     * @param view
     */
    public static void showSoftKeyBoard(View view){
        try {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * 监听输入框
     * @param et
     * @param tv
     */
    public static void seteditTextChangeListener(EditText et, TextView tv){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!s.toString().equals("")) {
                    tv.setText("搜索");
                } else {
                    tv.setText("取消");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    tv.setText("搜索");
                } else {
                    tv.setText("取消");
                }
            }
        });
    }


    /**
     *
     * 复制文本到剪切板
     */
    public static void copyContent(Context context,String copyText) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyText);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

    }
    /**
     * 返回当前程序版本号
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static long getAppVersionCode(Context context) {
        long versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.getLongVersionCode();
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName=null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 格式化长度
     * @param files
     * @return
     */
    public static String formetFileSize(long files){
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (files == 0L) {
            return wrongSize;
        }
        if (files < 1024) {
            fileSizeString = df.format((double) files).toString() + "B";
        } else if (files < 1048576) {
            fileSizeString =df.format((double) files / 1024).toString() + "KB";
        } else if (files < 1073741824) {
            fileSizeString =df.format((double) files / 1048576).toString() + "MB";
        } else {
            fileSizeString = df.format((double) files / 1073741824).toString() + "GB";
        }

        return fileSizeString;


    }


    /**
     *
     * 安装apk
     * @param apkFile
     * @param context
     */
    public static void installApk(File apkFile,Context context){
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            Uri fileUri = FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName() + ".fileprovider",apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri,context.getContentResolver().getType(fileUri));
            context.startActivity(intent);
        } else {
            Uri uri = Uri.fromFile(apkFile);
            intent.setAction(Intent.ACTION_VIEW);
            // 指定数据和类型
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    /**
     *
     * 重启应用
     * @param context
     */
    public static void restartApp(Context context){
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return;
        }
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }


    }

    /**
     *
     * 判断是什么模式
     */
    public static void darkNormal() {
        if (CacheUtils.getInstance().getFollowSystem()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        } else {
            if (CacheUtils.getInstance().getNormalDark()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }

    /**
     *
     * 设置颜色
     * @param editText
     * @param color
     */
    public static void setCursorDrawableColor(EditText editText, int color) {
        try {
            Field cursorDrawableResField = TextView.class.getDeclaredField("mCursorDrawableRes");
            cursorDrawableResField.setAccessible(true);
            int cursorDrawableRes = cursorDrawableResField.getInt(editText);
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editor = editorField.get(editText);
            Class<?> clazz = editor.getClass();
            Resources res = editText.getContext().getResources();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Field drawableForCursorField = clazz.getDeclaredField("mDrawableForCursor");
                drawableForCursorField.setAccessible(true);
                Drawable drawable = res.getDrawable(cursorDrawableRes,editText.getContext().getTheme());
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                drawableForCursorField.set(editor, drawable);
            } else {
                Field cursorDrawableField = clazz.getDeclaredField("mCursorDrawable");
                cursorDrawableField.setAccessible(true);
                Drawable[] drawables = new Drawable[2];
                drawables[0] = ContextCompat.getDrawable(editText.getContext(),cursorDrawableRes);
                drawables[1] = ContextCompat.getDrawable(editText.getContext(),cursorDrawableRes);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    drawables[0].setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_IN));
                    drawables[1].setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_IN));
                } else {
                    drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
                cursorDrawableField.set(editor, drawables);
            }
        } catch (Throwable t) {

        }
    }









}
