package com.knight.wanandroid.library_permiss;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/5 17:43
 * @descript:权限请求拦截器
 */
public interface IPermissionInterceptor {
    /**
     * 权限申请拦截，可在此处先弹 Dialog 再申请权限
     */
    default void requestPermissions(FragmentActivity activity, OnPermissionCallback callback, List<String> allPermissions) {
        PermissionFragment.beginRequest(activity, new ArrayList<>(allPermissions), this, callback);
    }

    /**
     * 权限授予回调拦截，参见 {@link OnPermissionCallback#onGranted(List, boolean)}
     */
    default void grantedPermissions(FragmentActivity activity, List<String> allPermissions,
                                    List<String> grantedPermissions, boolean all,
                                    OnPermissionCallback callback) {
        if (callback == null) {
            return;
        }
        callback.onGranted(grantedPermissions, all);
    }

    /**
     * 权限拒绝回调拦截，参见 {@link OnPermissionCallback#onDenied(List, boolean)}
     */
    default void deniedPermissions(FragmentActivity activity, List<String> allPermissions,
                                   List<String> deniedPermissions, boolean never,
                                   OnPermissionCallback callback) {
        if (callback == null) {
            return;
        }
        callback.onDenied(deniedPermissions, never);
    }
}
