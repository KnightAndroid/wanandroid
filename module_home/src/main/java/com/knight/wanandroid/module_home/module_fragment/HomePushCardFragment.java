package com.knight.wanandroid.module_home.module_fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.basefragment.BaseDBFragment;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.imageengine.ImageLoader;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomePushcardFragmentBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/24 14:41
 * @descript:
 */
public class HomePushCardFragment extends BaseDBFragment<HomePushcardFragmentBinding> {


    private String picLink;
    private String articleTitle;
    private String articleDesc;
    private String author;
    private String articleLink;


    public static HomePushCardFragment newInstance(String picLink, String articleTitle, String articleDesc, String author, String articleLink) {
        HomePushCardFragment projectViewpagerFragment = new HomePushCardFragment();
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
        return R.layout.home_pushcard_fragment;
    }



    @Override
    protected void setThemeColor(boolean isDarkMode) {

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
        ImageLoader.loadStringPhoto(getActivity(), picLink, mDatabind.ivEverydayPushpicture);
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
