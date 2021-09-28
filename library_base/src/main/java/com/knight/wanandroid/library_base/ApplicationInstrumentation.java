package com.knight.wanandroid.library_base;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import com.knight.wanandroid.library_util.SystemUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/27 18:52
 * @descript:
 */
public class ApplicationInstrumentation extends Instrumentation {
    private static final String TAG = "ApplicationInstrumentation";

    // ActivityThread中原始的对象, 保存起来
    Instrumentation mBase;
    private String checkName;

    public ApplicationInstrumentation(Instrumentation base,String checkName) {
        mBase = base;
        this.checkName = checkName;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className,
                                Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        className = SystemUtils.getActivityName(className,checkName);
        return mBase.newActivity(cl, className, intent);
    }

}
