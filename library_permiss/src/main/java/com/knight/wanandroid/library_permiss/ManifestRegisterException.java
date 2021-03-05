package com.knight.wanandroid.library_permiss;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/5 18:16
 * @descript:
 */
public final class ManifestRegisterException extends RuntimeException{

    ManifestRegisterException(){
        //清单文件中没有注册任何权限
        super("No permissions are registered in the manifest file");
    }

    ManifestRegisterException(String permission) {
        // 申请的危险权限没有在清单文件中注册
        super(permission + ": Permissions are not registered in the manifest file");
    }
}
