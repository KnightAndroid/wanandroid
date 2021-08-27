package com.knight.wanandroid.module_web.module_utils;

import android.view.View;
import android.webkit.WebView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.module_web.R;
import com.knight.wanandroid.module_web.module_enum.WebViewHitResult;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/26 18:10
 * @descript:
 */
public class ViewBindUtils {


    /**
     *
     * 查看网页图片
     * @param mWebView
     */
    public static void previewWebViewPhoto(WebView mWebView) {
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult hitTestResult = mWebView.getHitTestResult();
                WebViewHitResult webViewHitResult = new WebViewHitResult(hitTestResult);
                switch (webViewHitResult.getType()) {
                    case IMAGE_TYPE://处理长安图片的菜单项
                    case IMAGE_ANCHOR_TYPE://图片类型
                    case SRC_IMAGE_ANCHOR_TYPE://带有链接的图片类型
                        ARouter.getInstance().build(RoutePathActivity.Web.Web_PreviewPhoto)
                                .withString("photoUri",webViewHitResult.getResult())
                                .withTransition(R.anim.web_fade_out_anim,R.anim.web_fade_in_anim)
                                .navigation(mWebView.getContext());
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
