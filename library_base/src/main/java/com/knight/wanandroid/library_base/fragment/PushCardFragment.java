package com.knight.wanandroid.library_base.fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.basefragment.BaseDBFragment;
import com.knight.wanandroid.library_base.databinding.BasePushcardFragmentBinding;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/24 14:41
 * @descript:
 */
public class PushCardFragment extends BaseDBFragment<BasePushcardFragmentBinding> {


    private String picLink;
    private String articleTitle;
    private String articleDesc;
    private String author;
    private String articleLink;


    public static PushCardFragment newInstance(String picLink, String articleTitle, String articleDesc, String author, String articleLink) {
        PushCardFragment projectViewpagerFragment = new PushCardFragment();
        Bundle args = new Bundle();
        args.putString("picLink", picLink);
        args.putString("articleTitle", articleTitle);
        args.putString("articleDesc", articleDesc);
        args.putString("author", author);
        args.putString("articleLink", articleLink);
        projectViewpagerFragment.setArguments(args);
        return projectViewpagerFragment;
    }


    @Override
    protected int layoutId() {
        return R.layout.base_pushcard_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        picLink = getArguments().getString("picLink");
        articleTitle = getArguments().getString("articleTitle");
        articleDesc = getArguments().getString("articleDesc");
        author = getArguments().getString("author");
        articleLink = getArguments().getString("articleLink");
        //设置图像
        GlideEngineUtils.getInstance().loadStringPhoto(getActivity(), picLink, mDatabind.ivEverydayPushpicture);
        //设置作者
        mDatabind.tvArticleAuthor.setText(author);
        //标题
        mDatabind.tvArticleTitle.setText(articleTitle);
        //描述
        mDatabind.tvArticleDesc.setText(articleDesc);

    }


    public class ProxyClick {
        public void goWebActivity() {
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl", articleLink)
                    .withString("webTitle", articleTitle)
                    .navigation();
        }
    }


}
