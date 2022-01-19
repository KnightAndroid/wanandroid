package com.knight.wanandroid.library_permiss;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/5 17:45
 * @descript:权限请求结构回调接口
 */
public interface OnPermissionCallback {


    /**
     * 有权限被同意授予时回调
     *
     * @param permissions 请求成功的权限组
     * @param all         是否全部授予了
     */
    void onGranted(List<String> permissions, boolean all);

    /**
     * 有权限被拒绝授予时回调
     *
     * @param permissions 请求失败的权限组
     * @param never       是否有某个权限被永久拒绝了
     */
    default void onDenied(List<String> permissions, boolean never) {
    }
}
