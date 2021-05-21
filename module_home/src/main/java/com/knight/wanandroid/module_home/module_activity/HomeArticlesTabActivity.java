package com.knight.wanandroid.module_home.module_activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.BlurBuilder;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.library_widget.pagertransformer.CustPagerTransformer;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeArticlesTabActivityBinding;
import com.knight.wanandroid.module_home.module_fragment.HomeTopTabsFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/21 13:52
 * @descript:
 */
@Route(path = RoutePathActivity.Home.HomeTopArticle)
public class HomeArticlesTabActivity extends BaseDBActivity<HomeArticlesTabActivityBinding> {


    private List<Fragment> mHomeTopTabsFragments = new ArrayList<>();

    private  CompositePageTransformer mCompositePageTransformer;
    @Override
    public int layoutId() {
        return R.layout.home_articles_tab_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mDatabind.homeIv.setImageBitmap(BlurBuilder.blur(mDatabind.homeIv));
        if(BlurBuilder.isBlurFlag()){
            mDatabind.homeIv.setVisibility(View.VISIBLE);
        }


        for(int i = 0;i< 5;i++) {
            mHomeTopTabsFragments.add(new HomeTopTabsFragment());

        }

        mCompositePageTransformer = ViewSetUtils.setViewPage2(mDatabind.homeArticleViewpager, ScreenUtils.dp2px(20),ScreenUtils.dp2px(25));
        mCompositePageTransformer.addTransformer(new CustPagerTransformer(0.85f));
        ViewSetUtils.setViewPager2Init(this, mHomeTopTabsFragments, mDatabind.homeArticleViewpager, true);




    }

    @Override
    public void finish(){
        super.finish();
        BlurBuilder.recycle();
    }




}
