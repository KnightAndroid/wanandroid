package com.knight.wanandroid.library_base.util.web;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.knight.wanandroid.library_network.request.DownloadRequest;
import com.knight.wanandroid.library_util.CacheFileUtils;
import com.knight.wanandroid.library_util.DownLoadManagerUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import okio.ByteString;

/**
 * @ProjectName: wanandroid
 * @Package: com.knight.wanandroid.library_base.util.web
 * @ClassName: WebViewHelper
 * @Description: java类作用描述
 * @Author: knight
 * @CreateDate: 2022/7/5 9:14 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/7/5 9:14 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class WebViewHelper {

    private boolean injectState = false;
    private boolean injectVConsole = false;
    private String originalUrl = "about:blank";

    private WebView mView;


    public WebViewHelper(WebView mView) {
        this.mView = mView;
        this.mView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl() != null) {
                    if (view != null && !("http".equals(request.getUrl().getScheme()) || "https".equals(request.getUrl().getScheme()))) {
                        try {
                            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,request.getUrl()));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;
                    }

                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {
                if (view != null && request != null) {
                    if (canAssetsResource(request)) {
                        return assetsResourceRequest(view.getContext(),request);
                    }
                    if (canCacheResource(request)) {
                        return cacheResourceRequest(view.getContext(),request);
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView webView, String url, Bitmap favicon) {
                super.onPageStarted(webView, url, favicon);
                onPageChangeListener.onPageStarted(webView, url, favicon);
                injectState = false;
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView,url);
                onPageChangeListener.onPageFinished(webView,url);
                injectState = false;
            }
        });

        this.mView.setWebChromeClient(new WebChromeClient());
    }

    public interface OnPageChangeListener{
        void onPageStarted(WebView view,String url,Bitmap favicon);
        void onPageFinished(WebView view,String url);
        void onProgressChanged(WebView view,int newProgress);
    }

   private OnPageChangeListener onPageChangeListener;


   public WebViewHelper setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
       this.onPageChangeListener = onPageChangeListener;
       return this;
   }






    private boolean canAssetsResource(WebResourceRequest webRequest) {
        String url = webRequest.getUrl().toString();
        return url.startsWith("file://android_asser/");

    }

    private boolean canCacheResource(WebResourceRequest webResourceRequest) {
        String url = webResourceRequest.getUrl().toString();
        String extension = getExtensionFromUrl(url);
        return extension.equals("ico") || extension.equals("bmp") || extension.equals("gif")
                || extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")
                || extension.equals("svg") || extension.equals("webp") || extension.equals("css")
                || extension.equals("js") || extension.equals("json") || extension.equals("eot")
                || extension.equals("otf") || extension.equals("ttf") || extension.equals("woff");

    }


    private WebResourceResponse assetsResourceRequest(Context context,WebResourceRequest webRequest) {

        try {
            String url = webRequest.getUrl().toString();
            int filenameIndex = url.lastIndexOf("/") + 1;
            String filename = url.substring(filenameIndex);
            int suffixIndex = url.lastIndexOf(".");
            String suffix = url.substring(suffixIndex + 1);
            WebResourceResponse webResourceResponse = new WebResourceResponse(getMimeTypeFromUrl(url),"UTF-8",context.getAssets().open(suffix+"/"+filename));
            HashMap<String,String> map = new HashMap();
            map.put("access-control-allow-origin","*");
            webResourceResponse.setResponseHeaders(map);
            return webResourceResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private WebResourceResponse cacheResourceRequest(Context context,WebResourceRequest webRequest) {
        try {
            String url = webRequest.getUrl().toString();
            String cachePath = CacheFileUtils.getDirPath(context,"web_cache");
            String filePathName = cachePath + File.separator + ByteString.encodeUtf8(url).md5().hex();
            File file = new File(filePathName);
//             if (!file.exists() || !file.isFile()) {
//               new DownloadRequest()
//            }
            if (file.exists() && file.isFile()) {
                WebResourceResponse webResourceResponse = new WebResourceResponse(getMimeTypeFromUrl(url),"UTF-8",new FileInputStream(file));
                HashMap<String,String> map = new HashMap();
                map.put("access-control-allow-origin","*");
                webResourceResponse.setResponseHeaders(map);
                return webResourceResponse;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }


    private String getExtensionFromUrl(String url) {
        try {
            if (!TextUtils.isEmpty(url)) {
                return MimeTypeMap.getFileExtensionFromUrl(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    private String getMimeTypeFromUrl(String url) {
        try {
            String extension = getExtensionFromUrl(url);
            if (!TextUtils.isEmpty(extension)) {
                if ("json".equals(extension)) {
                    return "application/json";
                }
                if (MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) != null) {
                    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                } else {
                    return "*/*";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "*/*";
    }





}
