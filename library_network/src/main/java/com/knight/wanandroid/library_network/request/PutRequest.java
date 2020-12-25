package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.data.HttpMethod;

import androidx.lifecycle.LifecycleOwner;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:13
 * @descript:Put 请求
 */
public final class PutRequest extends BodyRequest<PutRequest> {

    public PutRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    protected String getRequestMethod() {
        return HttpMethod.PUT.toString();
    }
}
