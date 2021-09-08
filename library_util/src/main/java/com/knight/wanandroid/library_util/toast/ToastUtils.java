package com.knight.wanandroid.library_util.toast;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;

import com.knight.wanandroid.library_util.toast.callback.ToastInterceptorInterface;
import com.knight.wanandroid.library_util.toast.callback.ToastStrategyInterface;
import com.knight.wanandroid.library_util.toast.callback.ToastStyleInterface;
import com.knight.wanandroid.library_util.toast.style.BlackToastStyle;
import com.knight.wanandroid.library_util.toast.style.LocationToastStyle;
import com.knight.wanandroid.library_util.toast.style.ViewToastStyle;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/2 10:56
 * @descript:简单吐司工具类
 */
public final class ToastUtils {

    /** Application 对象 */
    private static Application sApplication;

    /** Toast 处理策略 */
    private static ToastStrategyInterface sToastStrategy;

    /** Toast 样式 */
    private static ToastStyleInterface<?> sToastStyle;

    /** Toast 拦截器（可空） */
    private static ToastInterceptorInterface sToastInterceptor;

    /** 调试模式 */
    private static Boolean sDebugMode;

    /**
     * 不允许被外部实例化
     */
    private ToastUtils() {}

    /**
     * 初始化 Toast，需要在 Application.create 中初始化
     *
     * @param application       应用的上下文
     */
    public static void init(Application application) {
        init(application, sToastStyle);
    }

    /**
     * 初始化 Toast 及样式
     */
    public static void init(Application application, ToastStyleInterface<?> style) {
        sApplication = application;

        // 初始化 Toast 显示处理器
        if (sToastStrategy == null) {
            setStrategy(new ToastStrategy());
        }

        if (style == null) {
            style = new BlackToastStyle();
        }

        // 设置 Toast 样式
        setStyle(style);
    }

    /**
     * 判断当前框架是否已经初始化
     */
    public static boolean isInit() {
        return sApplication != null && sToastStrategy != null && sToastStyle != null;
    }

    /**
     * 显示一个对象的吐司
     *
     * @param object      对象
     */
    public static void show(Object object) {
        show(object != null ? object.toString() : "null");
    }

    public static void debugShow(Object object) {
        if (!isDebugMode()) {
            return;
        }
        show(object);
    }

    /**
     * 显示一个吐司
     *
     * @param id      如果传入的是正确的 string id 就显示对应字符串
     *                如果不是则显示一个整数的string
     */
    public static void show(int id) {
        try {
            // 如果这是一个资源 id
            show(sApplication.getResources().getText(id));
        } catch (Resources.NotFoundException ignored) {
            // 如果这是一个 int 整数
            show(String.valueOf(id));
        }
    }

    public static void debugShow(int id) {
        if (!isDebugMode()) {
            return;
        }
        show(id);
    }

    /**
     * 显示一个吐司
     *
     * @param text      需要显示的文本
     */
    public static void show(CharSequence text) {
        // 如果是空对象或者空文本就不显示
        if (text == null || text.length() == 0) {
            return;
        }

        if (sToastInterceptor != null && sToastInterceptor.intercept(text)) {
            return;
        }

        sToastStrategy.showToast(text);
    }

    public static void debugShow(CharSequence text) {
        if (!isDebugMode()) {
            return;
        }
        show(text);
    }

    /**
     * 取消吐司的显示
     */
    public static void cancel() {
        sToastStrategy.cancelToast();
    }

    /**
     * 设置吐司的位置
     *
     * @param gravity           重心
     */
    public static void setGravity(int gravity) {
        setGravity(gravity, 0, 0);
    }

    public static void setGravity(int gravity, int xOffset, int yOffset) {
        setGravity(gravity, xOffset, yOffset, 0, 0);
    }

    public static void setGravity(int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin) {
        sToastStrategy.bindStyle(new LocationToastStyle(sToastStyle, gravity, xOffset, yOffset, horizontalMargin, verticalMargin));
    }

    /**
     * 给当前 Toast 设置新的布局
     */
    public static void setView(int id) {
        if (id <= 0) {
            return;
        }
        setStyle(new ViewToastStyle(id, sToastStyle));
    }

    /**
     * 初始化全局的 Toast 样式
     *
     * @param style         样式实现类，框架已经实现两种不同的样式
     *                      黑色样式：{@link BlackToastStyle}
     *                      白色样式：{@link com.knight.wanandroid.library_util.toast.style.WhiteToastStyle}
     */
    public static void setStyle(ToastStyleInterface<?> style) {
        sToastStyle = style;
        sToastStrategy.bindStyle(style);
    }

    public static ToastStyleInterface<?> getStyle() {
        return sToastStyle;
    }

    /**
     * 设置 Toast 显示策略
     */
    public static void setStrategy(ToastStrategyInterface strategy) {
        sToastStrategy = strategy;
        sToastStrategy.registerStrategy(sApplication);
    }

    public static ToastStrategyInterface getStrategy() {
        return sToastStrategy;
    }

    /**
     * 设置 Toast 拦截器（可以根据显示的内容决定是否拦截这个Toast）
     * 场景：打印 Toast 内容日志、根据 Toast 内容是否包含敏感字来动态切换其他方式显示（这里可以使用我的另外一套框架 XToast）
     */
    public static void setInterceptor(ToastInterceptorInterface interceptor) {
        sToastInterceptor = interceptor;
    }

    public static ToastInterceptorInterface getInterceptor() {
        return sToastInterceptor;
    }

    /**
     * 是否为调试模式
     */
    public static void setDebugMode(boolean debug) {
        sDebugMode = debug;
    }

    private static boolean isDebugMode() {
        if (sDebugMode == null) {
            sDebugMode = (sApplication.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
        return sDebugMode;
    }


//    /**
//     *
//     * 针对华为安全键盘的吐司情况
//     * @param context
//     * @param message
//     */
//    public void showHuaweiToast(Context context,String message){
//        if(mToast == null){
//            Toast.makeText(context,message,Toast.LENGTH_SHORT);
//        } else {
//            mToast.cancel();
//            Toast.makeText(context,message,Toast.LENGTH_SHORT);
//        }
//        mToast.show();
//        if(MobileUtils.isEMUI() && context instanceof Activity){
//            View focusView = ((Activity) context).getWindow().getDecorView().findFocus();
//            if(focusView instanceof EditText){
//                if(((EditText) focusView).getInputType() == (InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT)){
//                    SystemUtils.hideSoftKeyboard((Activity) context);
//                }
//            }
//
//        }
//
//    }














}
