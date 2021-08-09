package com.knight.wanandroid.library_network.interceptor;

import com.knight.wanandroid.library_common.provider.ApplicationProvider;
import com.knight.wanandroid.library_common.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/9 11:16
 * @descript:
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        if (!NetWorkUtils.isNetWorkConnected(ApplicationProvider.getInstance().getApplication())) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response response = chain.proceed(request);
        if (NetWorkUtils.isNetWorkConnected(ApplicationProvider.getInstance().getApplication())) {
            int maxTime = 180;
            // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
            String cacheControl = request.cacheControl().toString();
            response.newBuilder()
                    .header("Cache-Control", "public, max-age="+maxTime)
                    .removeHeader("Pragma")
                    .build();
        } else {
            //无网络连接时 设置超时为4周  只对get有用,post没有缓冲
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
