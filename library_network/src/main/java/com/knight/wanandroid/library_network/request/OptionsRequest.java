package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.data.HttpMethod;

import androidx.lifecycle.LifecycleOwner;

/**
 * @author created by luguian
 * @organize 车童网
 * @Date 2021/8/30 17:01
 * @descript:
 */
public final class OptionsRequest extends UrlRequest<OptionsRequest> {

    public OptionsRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    protected String getRequestMethod() {
        return HttpMethod.OPTIONS.toString();
    }
}
