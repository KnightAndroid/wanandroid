package com.knight.wanandroid.module_web.module_fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.webkit.WebView;

import com.knight.wanandroid.library_base.basefragment.BaseDBDialogFragment;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_web.R;
import com.knight.wanandroid.module_web.databinding.WebNormalBottomFragmentBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/16 9:33
 * @descript:
 */
public class WebNormalBottomFragment extends BaseDBDialogFragment<WebNormalBottomFragmentBinding> {

    private String url;
    private WebView mWebView;

    public WebNormalBottomFragment(String url,WebView mWebView){
        this.url = url;
        this.mWebView = mWebView;

    }

    public static WebNormalBottomFragment newInstance(String url,WebView mWebView){
        WebNormalBottomFragment webNormalBottomFragment = new WebNormalBottomFragment(url,mWebView);
        return webNormalBottomFragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.web_normal_bottom_fragment;
    }

    @Override
    protected void initView() {
        mDatabind.setClick(new ProcyClick());
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }


    public class ProcyClick{
        //复制网址
        public void copyUrl(){
            SystemUtils.copyContent(getActivity(),url);
            ToastUtils.getInstance().showToast(getString(R.string.web_success_copyurl));
            dismiss();
        }

        //刷新
        public void refresh(){
            mWebView.reload();
            dismiss();
        }

        //用外部浏览器打开
        public void openBrowser(){
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            startActivity(intent);
            dismiss();
        }
    }
}
