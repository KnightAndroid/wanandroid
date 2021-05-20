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
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.LoveAnimatorRelativeLayout;
import com.knight.wanandroid.module_web.R;
import com.knight.wanandroid.module_web.databinding.WebActivityMainBinding;
import com.knight.wanandroid.module_web.module_contract.WebContract;
import com.knight.wanandroid.module_web.module_fragment.WebBottomFragment;
import com.knight.wanandroid.module_web.module_model.WebModel;
import com.knight.wanandroid.module_web.module_presenter.WebPresenter;
import com.knight.wanandroid.module_web.module_view.WebLayout;

import org.greenrobot.eventbus.EventBus;

import androidx.core.content.ContextCompat;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/10 18:58
 * @descript:
 */
@Route(path = RoutePathActivity.Web.Web_Pager)
public class WebActivity extends BaseActivity<WebActivityMainBinding, WebPresenter, WebModel> implements WebContract.WebView,LoveAnimatorRelativeLayout.onCollectListener {

    //文章url
    @Autowired(name = "webUrl")
    String webUrl = "";

    //文章标题
    @Autowired(name = "title")
    String title = "";
    private AgentWeb mAgentWeb;

    //文章id
    @Autowired(name = "articleId")
    int articleId = 0;

    //是否收藏文章
    @Autowired(name = "isCollect")
    boolean isCollect;

    private WebView mWebView;




    @Override
    public int layoutId() {
        return R.layout.web_activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mDatabind.includeWebToolbar.baseIvRight.setVisibility(View.VISIBLE);
        mDatabind.webLikeRl.setOnCollectListener(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mDatabind.webLl, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ContextCompat.getColor(this,R.color.base_color_theme),2)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(webUrl);

        mWebView = mAgentWeb.getWebCreator().getWebView();


        initWebView(mWebView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatabind.includeWebToolbar.baseTvTitle.setText(Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY));
        } else {
            mDatabind.includeWebToolbar.baseTvTitle.setText(Html.fromHtml(title));
        }
        mDatabind.includeWebToolbar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includeWebToolbar.baseIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebBottomFragment.newInstance(webUrl,title,articleId,isCollect).show(getSupportFragmentManager(),"dialog_web");
            }
        });


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
    protected void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();


    }

    @Override
    protected void onResume() {
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


    @Override
    public void onCollect() {
        requestCollect();
    }

    @LoginCheck
    private void requestCollect(){
        if (!isCollect) {
            mPresenter.requestCollectArticle(articleId);
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Override
    public void collectArticleSuccess() {
        isCollect = true;
        ToastUtils.getInstance().showToast("收藏成功");
        EventBus.getDefault().post(new EventBusUtils.CollectSuccess());
    }
}
