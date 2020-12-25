package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.data.HttpMethod;

import androidx.lifecycle.LifecycleOwner;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:10
 * @descript:Delete 请求
 */
public final class DeleteRequest extends BodyRequest<DeleteRequest> {

    public DeleteRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    protected String getRequestMethod() {
        return HttpMethod.DELETE.toString();
    }
}
