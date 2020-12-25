package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.data.HttpMethod;

import androidx.lifecycle.LifecycleOwner;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:12
 * @descript:Head 请求
 */
public final class HeadRequest extends UrlRequest<HeadRequest> {

    public HeadRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    protected String getRequestMethod() {
        return HttpMethod.HEAD.toString();
    }
}