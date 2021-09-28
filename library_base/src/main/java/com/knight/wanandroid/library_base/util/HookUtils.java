package com.knight.wanandroid.library_base.util;

import android.app.Instrumentation;
import android.content.Context;

import com.knight.wanandroid.library_base.ApplicationInstrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/27 17:42
 * @descript:
 */
public final class HookUtils {
    private static Object providers;

    private static Method installContentProvidersMethod;
    private static Object currentActivityThread;

    /**
     *
     * 用户同意之后再初始化contentProvider框架
     * @param context
     */
    public static void initProvider(Context context){
        try {
            installContentProvidersMethod.invoke(currentActivityThread,context,providers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void attachContext(String checkeName) throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        //currentActivityThread是一个static函数所以可以直接invoke，不需要带实例参数
        currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);
        // 创建代理对象
        Instrumentation evilInstrumentation = new ApplicationInstrumentation(mInstrumentation,checkeName);
        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);

        hookInstallContentProvider(activityThreadClass);
    }

    /**
     *
     * hookContentProvider
     * @param activityThreadClass
     * @throws Exception
     */
    private static void hookInstallContentProvider(Class activityThreadClass) throws Exception{
        Field appDataField = activityThreadClass.getDeclaredField("mBoundApplication");
        appDataField.setAccessible(true);
        Object appData= appDataField.get(currentActivityThread);
        Field providersField= appData.getClass().getDeclaredField("providers");
        providersField.setAccessible(true);
        providers = providersField.get(appData);
        //清空provider，避免有些sdk通过provider来初始化
        providersField.set(appData,null);
        installContentProvidersMethod = activityThreadClass.getDeclaredMethod("installContentProviders", Context.class, List.class);
        installContentProvidersMethod.setAccessible(true);
    }

}
