package com.knight.wanandroid.library_base.util.web;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.os.Build;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ArrayUtils;

import java.util.ArrayList;

/**
 * Author:Knight
 * Time:2022/7/6 10:35
 * Description:WebViewManager
 */
public class WebViewManager {


    private static volatile WebViewManager webViewManager;
    private ArrayList<WebView> webViewCache = new ArrayList<>(1);
    private WebViewManager(){

    }

    public static WebViewManager getInstance() {
        if (webViewManager == null) {
            synchronized (WebViewManager.class) {
                if (webViewManager == null) {
                    webViewManager = new WebViewManager();
                }
            }
        }
        return webViewManager;
    }

    public static void prepare(Context context) {
        getInstance().needPrepare(context);
    }

    public static WebView obtain(Context context) {
        return getInstance().needobtain(context);
    }


    public void recycle(WebView webView) {
        getInstance().needrecycle(webView);
    }

    public void destroy() {
        getInstance().needestroy();
    }

    private WebView create(Context context) {

        WebView webView = new WebView(context);
        WebSettings settings = webView.getSettings();
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        return webView;
    }


    private void needPrepare(Context context) {
        if (webViewCache.isEmpty()) {
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    webViewCache.add(create(new MutableContextWrapper(context)));
                    return false;
                }
            });
        }

    }


    private WebView needobtain(Context context) {
        if (webViewCache.isEmpty()) {
            webViewCache.add(create(new MutableContextWrapper(context)));
        }
        WebView webView = webViewCache.remove(0);
        MutableContextWrapper contextWrapper = (MutableContextWrapper) webView.getContext();
        contextWrapper.setBaseContext(context);
        webView.clearHistory();
        webView.resumeTimers();
        return webView;
    }

    private void needrecycle(WebView webView) {
        try {
            webView.stopLoading();
            webView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            webView.clearHistory();
            webView.pauseTimers();
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            ViewParent parent = webView.getParent();
            if (parent != null) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(webView);
                }
            }
            MutableContextWrapper contextWrapper = (MutableContextWrapper) webView.getContext();
            contextWrapper.setBaseContext(webView.getContext().getApplicationContext());

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (!webViewCache.contains(webView)) {
                webViewCache.add(webView);
            }
        }
    }


    private void needestroy() {
        try {
            for (WebView webView: webViewCache) {
                webView.removeAllViews();
                webView.destroy();
                webViewCache.remove(webView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
