package com.knight.wanandroid.library_network.config;

import java.lang.reflect.Type;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.Response;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 17:24
 * @descript:请求处理器
 */
public interface IRequestHandler {

    /**
     * 请求成功时回调
     *
     * @param lifecycle 有生命周期的对象（例如 Activity、Fragment）
     * @param response  响应对象
     * @param type      解析类型
     * @return 返回结果
     * @throws Exception 回调失败方法
     */
    Object requestSucceed(LifecycleOwner lifecycle, Response response, Type type) throws Exception;

    /**
     * 请求失败
     *
     * @param lifecycle 有生命周期的对象（例如 Activity、Fragment）
     * @param e         错误对象
     * @return 错误对象
     */
    Exception requestFail(LifecycleOwner lifecycle, Exception e);
}