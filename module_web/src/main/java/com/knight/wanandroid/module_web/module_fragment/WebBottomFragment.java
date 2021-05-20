package com.knight.wanandroid.module_web.module_fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.fragment.BaseDialogFragment;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_web.R;
import com.knight.wanandroid.module_web.databinding.WebFragmentDialogBinding;
import com.knight.wanandroid.module_web.module_contract.WebDialogContract;
import com.knight.wanandroid.module_web.module_model.WebDialogModel;
import com.knight.wanandroid.module_web.module_presenter.WebDialogPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 10:53
 * @descript: webview底部弹出框
 */
public class WebBottomFragment extends BaseDialogFragment<WebFragmentDialogBinding, WebDialogPresenter, WebDialogModel> implements WebDialogContract.WebDialogView {

    private String articleurl;
    private String articleTitle;
    private int articleId;
    private boolean collect;
    public WebBottomFragment(String articleurl,String articleTitle,int articleId,boolean collect){
        this.articleurl = articleurl;
        this.articleTitle = articleTitle;
        this.articleId = articleId;
        this.collect = collect;
    }

    public static WebBottomFragment newInstance(String url,String title,int articleId,boolean collect){
        WebBottomFragment webBottomFragment = new WebBottomFragment(url,title,articleId,collect);
        return webBottomFragment;
    }


    @Override
    protected int layoutId() {
        return R.layout.web_fragment_dialog;
    }


    @Override
    public void initView(){
        mDatabind.setClick(new ProcyClick());
        mDatabind.webCollectArticle.setVisibility(collect? View.GONE : View.VISIBLE);
    }

    @Override
    public void collectArticleSuccess() {
        ToastUtils.getInstance().showToast("收藏成功");
        EventBus.getDefault().post(new EventBusUtils.CollectSuccess());
        dismiss();
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


    public class ProcyClick{
        public void shareArticle(){
            if (!TextUtils.isEmpty(articleurl) && (articleurl.startsWith("http") || articleurl.startsWith("https"))) {
                Intent intent = new Intent(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT,getString(R.string.web_share_article_url,getString(R.string.base_app),articleTitle,articleurl));
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,getString(R.string.web_open_share)));
                dismiss();
            }
        }


        public void copyUrlContent(){
            SystemUtils.copyContent(getActivity(),articleurl);
            ToastUtils.getInstance().showToast("已成功复制网址");
            dismiss();
        }

        @LoginCheck
        public void collectArticle(){
            mPresenter.requestCollectArticle(articleId);

        }

        public void openBrowser(){
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(articleurl));
            startActivity(intent);
            dismiss();
        }
    }
}
