package com.knight.wanandroid.module_web.module_fragment;

import android.content.Intent;
import android.text.TextUtils;

import com.knight.wanandroid.library_base.fragment.BaseDialogFragment;
import com.knight.wanandroid.module_web.R;
import com.knight.wanandroid.module_web.databinding.WebFragmentDialogBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 10:53
 * @descript: webview底部弹出框
 */
public class WebBottomFragment extends BaseDialogFragment<WebFragmentDialogBinding> {

    private String articleurl;
    private String articleTitle;
    private int articleId;
    public WebBottomFragment(String articleurl,String articleTitle,int articleId){
        this.articleurl = articleurl;
        this.articleTitle = articleTitle;
        this.articleId = articleId;
    }

    public static WebBottomFragment newInstance(String url,String title,int articleId){
        WebBottomFragment webBottomFragment = new WebBottomFragment(url,title,articleId);
        return webBottomFragment;
    }


    @Override
    protected int layoutId() {
        return R.layout.web_fragment_dialog;
    }


    @Override
    public void initView(){
        mDatabind.setClick(new ProcyClick());
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

        public void collectArticle(){

        }
    }
}
