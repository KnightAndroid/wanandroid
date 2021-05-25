package com.knight.wanandroid.module_web.module_activity;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.appbar.AppBarLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.module_web.R;
import com.knight.wanandroid.module_web.databinding.WebTransitionActivityBinding;
import com.knight.wanandroid.module_web.module_view.WebLayout;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/24 17:09
 * @descript:
 */

@Route(path = RoutePathActivity.Web.Web_Transition)
public class WebTransitionActivity extends BaseDBActivity<WebTransitionActivityBinding> {




    @Autowired(name = "cardBgColor")
    int cardBgColor;

    //文章url
    @Autowired(name = "webUrl")
    String webUrl = "";

    //文章标题
    @Autowired(name = "title")
    String title = "";
    private AgentWeb mAgentWeb;

    //作者
    @Autowired(name = "author")
    String author = "";

    //分类
    @Autowired(name = "chapterName")
    String chapterName = "";

//    //文章id
//    @Autowired(name = "articleId")
//    int articleId = 0;

//    //是否收藏文章
//    @Autowired(name = "isCollect")
//    boolean isCollect;

    private WebView mWebView;

    private float alphaMaxOffset = ScreenUtils.dp2px(100);

    @Override
    public int layoutId() {
        return R.layout.web_transition_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mDatabind.webTransitionToolbar.baseIvBack.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            }else{
                finish();
            }

        });
        initTranstionData();
        initWebView();
        mDatabind.webAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // 设置 toolbar 背景
                mDatabind.webImage.getBackground().setAlpha(255 - (int) (255 * -verticalOffset / (float)alphaMaxOffset));
                if (255 - (int) (255 * -verticalOffset / (float)alphaMaxOffset) > 127) {
                      mDatabind.webTransitionToolbar.baseTvTitle.setTextColor(ContextCompat.getColor(WebTransitionActivity.this,R.color.web_white));
                } else {
                    mDatabind.webTransitionToolbar.baseTvTitle.setTextColor(ContextCompat.getColor(WebTransitionActivity.this,R.color.black));
                }
            }

        });

        mDatabind.webCollbarlayout.post(() -> alphaMaxOffset = mDatabind.webCollbarlayout.getHeight() - mDatabind.webToolbar.getHeight());

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

    private void initTranstionData(){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(cardBgColor);
        mDatabind.webImage.setBackground(gradientDrawable);
        ViewCompat.setTransitionName(mDatabind.webImage, AppConfig.IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(mDatabind.webArticleAuthor,AppConfig.TEXT_AUTHOR_NAME);
        ViewCompat.setTransitionName(mDatabind.webChapterName,AppConfig.TEXT_CHAPTERNAME_NAME);

        mDatabind.webArticleAuthor.setText(author);
        mDatabind.webChapterName.setText(chapterName);
        mDatabind.webTransitionToolbar.baseTvTitle.setTextColor(ContextCompat.getColor(this,R.color.web_white));
        mDatabind.webTransitionToolbar.baseTvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatabind.webTransitionToolbar.baseTvTitle.setText(Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY));
        } else {
            mDatabind.webTransitionToolbar.baseTvTitle.setText(Html.fromHtml(title));
        }
        mDatabind.webImage.getBackground().setAlpha(255);

    }

    private void initWebView(){
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mDatabind.webNestscrollview, new ViewGroup.LayoutParams(-1,-1))
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
        WebSettings settings = mWebView.getSettings();
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
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
}
