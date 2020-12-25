package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.data.HttpMethod;

import androidx.lifecycle.LifecycleOwner;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:11
 * @descript:
 */
public final class GetRequest extends UrlRequest<GetRequest> {

    public GetRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    protected String getRequestMethod() {
        return HttpMethod.GET.toString();
    }
}
