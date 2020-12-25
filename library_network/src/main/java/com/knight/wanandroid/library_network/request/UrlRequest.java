package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.data.BodyType;
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
 * @descript:
 */
public abstract class UrlRequest<T extends UrlRequest> extends BaseRequest<T> {

    private CacheControl mCacheControl;

    public UrlRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    /**
     * 设置缓存模式
     */
    public T cache(CacheControl cacheControl) {
        mCacheControl = cacheControl;
        return (T) this;
    }

    @Override
    protected Request createRequest(String url, String tag, HttpParams params, HttpHeaders headers, BodyType type) {
        Request.Builder request = new Request.Builder();
        if (mCacheControl != null) {
            request.cacheControl(mCacheControl);
        }

        if (tag != null) {
            request.tag(tag);
        }

        // 添加请求头
        if (!headers.isEmpty()) {
            for (String key : headers.getNames()) {
                request.addHeader(key, headers.get(key));
            }
        }

        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        // 添加参数
        if (!params.isEmpty()) {
            for (String key : params.getNames()) {
                builder.addEncodedQueryParameter(key, String.valueOf(params.get(key)));
            }
        }
        HttpUrl link = builder.build();
        request.url(link);
        request.method(getRequestMethod(), null);

        EasyLog.print("RequestUrl", String.valueOf(link));
        EasyLog.print("RequestMethod", getRequestMethod());
        return request.build();
    }
}