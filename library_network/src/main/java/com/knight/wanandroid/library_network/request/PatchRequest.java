package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.data.HttpMethod;

import androidx.lifecycle.LifecycleOwner;

/**
 * @author created by knight
 * @organize
 * @Date 2020/12/25 18:12
 * @descript:Patch请求
 */
public final class PatchRequest extends BodyRequest<PatchRequest> {

    public PatchRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    protected String getRequestMethod() {
        return HttpMethod.PATCH.toString();
    }
}
