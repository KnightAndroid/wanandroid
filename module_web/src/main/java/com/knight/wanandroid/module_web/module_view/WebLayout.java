package com.knight.wanandroid.module_web.module_view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.just.agentweb.IWebLayout;
import com.knight.wanandroid.module_web.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/10 18:55
 * @descript:
 */
public class WebLayout implements IWebLayout {


    private WebView mWebView;

    public WebLayout(Activity actiivty){
        mWebView = (WebView) LayoutInflater.from(actiivty).inflate(R.layout.web_layout_web,null);


    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mWebView;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }
}
