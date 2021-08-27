package com.knight.wanandroid.module_web.module_enum;

import android.webkit.WebView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/26 11:30
 * @descript:
 */
public class WebViewHitResult {

    private WebView.HitTestResult mHitTestResult;

    public WebViewHitResult(WebView.HitTestResult mHitTestResult) {
        this.mHitTestResult = mHitTestResult;
    }

    public String getResult() {
        return mHitTestResult.getExtra();
    }


    public WebViewHitResultEnum getType() {
        switch(mHitTestResult.getType()) {
            case WebView.HitTestResult.ANCHOR_TYPE:
                return WebViewHitResultEnum.ANCHOR_TYPE;
            case WebView.HitTestResult.PHONE_TYPE:
                return WebViewHitResultEnum.PHONE_TYPE;
            case WebView.HitTestResult.GEO_TYPE:
                return WebViewHitResultEnum.GEO_TYPE;
            case WebView.HitTestResult.EMAIL_TYPE:
                return WebViewHitResultEnum.EMAIL_TYPE;
            case WebView.HitTestResult.IMAGE_TYPE:
                return WebViewHitResultEnum.IMAGE_TYPE;
            case WebView.HitTestResult.IMAGE_ANCHOR_TYPE:
                return WebViewHitResultEnum.IMAGE_ANCHOR_TYPE;
            case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                return WebViewHitResultEnum.SRC_ANCHOR_TYPE;
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                return WebViewHitResultEnum.SRC_IMAGE_ANCHOR_TYPE;
            case WebView.HitTestResult.EDIT_TEXT_TYPE:
                return WebViewHitResultEnum.EDIT_TEXT_TYPE;
            default:
                return WebViewHitResultEnum.UNKNOWN_TYPE;
        }
    }
}
