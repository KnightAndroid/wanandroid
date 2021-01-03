package com.knight.wanandroid.library_base.listener;

/**
 * @ProjectName: wanandroid
 * @Package: com.knight.wanandroid.library_base.listener
 * @ClassName: MvpListener
 * @Description: mvp监听回调
 * @Author: knight
 * @CreateDate: 2021/1/3 5:00 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/3 5:00 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public interface MvpListener<E> {


    /**
     * 成功的适合回调
     * @param data
     */
    void onSuccess(E data);


    /**
     *
     * 失败的适合回调
     *
     */
    void onError(String erroeMsg);
}
