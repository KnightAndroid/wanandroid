package com.knight.wanandroid.library_network.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:02
 * @descript:全局的生命周期策略
 */
public final class ApplicationLifecycle implements LifecycleOwner {

    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }
}