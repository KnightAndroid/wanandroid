package com.knight.wanandroid.module_web.module_activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.module_web.R;
import com.knight.wanandroid.module_web.databinding.WebNormalActivityBinding;
import com.knight.wanandroid.module_web.module_fragment.WebNormalBottomFragment;
import com.knight.wanandroid.module_web.module_utils.ViewBindUtils;
import com.knight.wanandroid.module_web.module_view.WebLayout;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/19 17:16
 * @descript:
 */

@Route(path = RoutePathActivity.Web.Web_Normal)
public final class WebNormalActivity extends BaseDBActivity<WebNormalActivityBinding> {

    @Autowired(name = "webUrl")
    String webUrl = "";


    @Autowired(name = "webTitle")
    String webTitle = "";
     


    private AgentWeb mAgentWeb;
    private WebView mWebView;

    @Override
    public int layoutId() {
        return R.layout.web_normal_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mDatabind.webNormalLl, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(CacheUtils.getThemeColor(),2)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                //打开其他应用时，弹窗咨询用户是否前往其他应用
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(webUrl);

        mWebView = mAgentWeb.getWebCreator().getWebView();
        initWebView(mWebView);
        ViewBindUtils.previewWebViewPhoto(mWebView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatabind.includeWebNormaltoolbar.baseTvTitle.setText(Html.fromHtml(webTitle,Html.FROM_HTML_MODE_LEGACY));
        } else {
            mDatabind.includeWebNormaltoolbar.baseTvTitle.setText(Html.fromHtml(webTitle));
        }

        mDatabind.includeWebNormaltoolbar.baseIvRight.setVisibility(View.VISIBLE);
        mDatabind.includeWebNormaltoolbar.baseIvRight.setOnClickListener(v ->
                WebNormalBottomFragment.newInstance(webUrl,mWebView).show(getSupportFragmentManager(),"dialog_webnormal"));
        mDatabind.includeWebNormaltoolbar.baseIvBack.setOnClickListener(v -> finish());


    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
    }

    private WebViewClient mWebViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }
    };

    private void initWebView(WebView webView){
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

    }

    @Override
    public void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();


    }

    @Override
    public void onResume() {
        super.onResume();
        mAgentWeb.getWebLifeCycle().onResume();

    }

    @Override
    public void onBackPressed(){
        if (mAgentWeb != null) {
            if(!mAgentWeb.back()){
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
