package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.HttpConfig;
import com.knight.wanandroid.library_network.data.BodyType;
import com.knight.wanandroid.library_network.data.CacheMode;
import com.knight.wanandroid.library_network.data.HttpHeaders;
import com.knight.wanandroid.library_network.data.HttpParams;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:14
 * @descript:不带 RequestBody 的请求
 */
public abstract class UrlRequest<T extends UrlRequest<?>> extends BaseRequest<T> {

    public UrlRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    protected Request createRequest(String url, String tag, HttpParams params, HttpHeaders headers, BodyType type) {
        Request.Builder requestBuilder = new Request.Builder();

        if (tag != null) {
            requestBuilder.tag(tag);
        }

        // 如果设置了不缓存数据
        if (getRequestCache().getMode() == CacheMode.NO_CACHE) {
            requestBuilder.cacheControl(new CacheControl.Builder().noCache().build());
        }

        // 添加请求头
        if (!headers.isEmpty()) {
            for (String key : headers.getNames()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }

        HttpUrl.Builder urlBuilder = HttpUrl.get(url).newBuilder();
        // 添加参数
        if (!params.isEmpty()) {
            for (String key : params.getNames()) {
                urlBuilder.addQueryParameter(key, String.valueOf(params.get(key)));
            }
        }
        HttpUrl link = urlBuilder.build();
        requestBuilder.url(link);
        requestBuilder.method(getRequestMethod(), null);

        EasyLog.print("RequestUrl", String.valueOf(link));
        EasyLog.print("RequestMethod", getRequestMethod());

        // 打印请求头和参数的日志
        if (HttpConfig.getInstance().isLogEnabled()) {

            if (!headers.isEmpty() || !params.isEmpty()) {
                EasyLog.print();
            }

            for (String key : headers.getNames()) {
                EasyLog.print(key, headers.get(key));
            }

            if (!headers.isEmpty() && !params.isEmpty()) {
                EasyLog.print();
            }

            for (String key : params.getNames()) {
                EasyLog.print(key, String.valueOf(params.get(key)));
            }

            if (!headers.isEmpty() || !params.isEmpty()) {
                EasyLog.print();
            }
        }

        return getRequestHandler().requestStart(getLifecycleOwner(), getRequestApi(), requestBuilder.build());
    }
}