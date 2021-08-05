package com.knight.wanandroid.module_home.module_fragment;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.basefragment.BaseDBFragment;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_widget.pagertransformer.DragLayout;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentToptabsBinding;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;

import java.io.Serializable;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/21 16:01
 * @descript:
 */
public class HomeTopTabsFragment extends BaseDBFragment<HomeFragmentToptabsBinding> implements DragLayout.GotoDetailListener {

    private TopArticleEntity mTopArticleEntity;
    private int cardBgColor;


    public static HomeTopTabsFragment newInstance(TopArticleEntity topArticleEntity){
        HomeTopTabsFragment projectViewpagerFragment = new HomeTopTabsFragment();
        Bundle args = new Bundle();
        args.putSerializable("topdata",(Serializable) topArticleEntity);
        projectViewpagerFragment.setArguments(args);
        return projectViewpagerFragment;
    }


    @Override
    protected int layoutId() {
        return R.layout.home_fragment_toptabs;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        GradientDrawable gradientChapterNameDrawable = new GradientDrawable();
        gradientChapterNameDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientChapterNameDrawable.setStroke(ScreenUtils.dp2px(1), CacheUtils.getInstance().getThemeColor());

        GradientDrawable gradientAuthorDrawable = new GradientDrawable();
        gradientAuthorDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientAuthorDrawable.setStroke(ScreenUtils.dp2px(1), CacheUtils.getInstance().getThemeColor());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDatabind.homeToparticleSuperchaptername.setBackground(gradientChapterNameDrawable);
            mDatabind.homeToparticleAuthor.setBackground(gradientAuthorDrawable);
        } else {
            mDatabind.homeToparticleSuperchaptername.setBackgroundDrawable(gradientChapterNameDrawable);
            mDatabind.homeToparticleAuthor.setBackgroundDrawable(gradientAuthorDrawable);

        }
        mDatabind.homeToparticleAuthor.setTextColor(themeColor);
        mDatabind.homeToparticleSuperchaptername.setTextColor(themeColor);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTopArticleEntity  = (TopArticleEntity) getArguments().getSerializable("topdata");
        mDatabind.homeToparticleDrawlayout.setGotoDetailListener(this);
        GradientDrawable gradientDrawable = new GradientDrawable();
        cardBgColor = ColorUtils.getRandColorCode();
        gradientDrawable.setColor(cardBgColor);
        mDatabind.homeToparticleIv.setBackground(gradientDrawable);
    }


    @Override
    public void initData(){
        //设置标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDatabind.homeTvToparticleTitle.setText(Html.fromHtml(mTopArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY));
            mDatabind.homeToparticleDesc.setText(Html.fromHtml(mTopArticleEntity.getDesc(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            mDatabind.homeTvToparticleTitle.setText(Html.fromHtml(mTopArticleEntity.getTitle()));
            mDatabind.homeToparticleDesc.setText(Html.fromHtml(mTopArticleEntity.getDesc()));
        }

        if (TextUtils.isEmpty(mTopArticleEntity.getDesc())) {
            mDatabind.homeToparticleDesc.setVisibility(View.GONE);
        } else {
            mDatabind.homeToparticleDesc.setVisibility(View.VISIBLE);
        }

        mDatabind.homeToparticleSuperchaptername.setText(mTopArticleEntity.getSuperChapterName());
        mDatabind.homeToparticleAuthor.setText(mTopArticleEntity.getAuthor());
        mDatabind.homeToparticleDate.setText(mTopArticleEntity.getNiceDate());

    }

    @Override
    public void gotoDetail() {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getContext(),
                new Pair(mDatabind.homeToparticleIv, AppConfig.IMAGE_TRANSITION_NAME),
                new Pair(mDatabind.homeToparticleAuthor,AppConfig.TEXT_AUTHOR_NAME),
                new Pair(mDatabind.homeToparticleSuperchaptername,AppConfig.TEXT_CHAPTERNAME_NAME));
        ARouter.getInstance().build(RoutePathActivity.Web.Web_Transition)
                .withInt("cardBgColor",cardBgColor)
                .withString("webUrl",mTopArticleEntity.getLink())
                .withString("title",mTopArticleEntity.getTitle())
                .withString("author",mTopArticleEntity.getAuthor())
                .withString("chapterName",mTopArticleEntity.getChapterName())
                .withOptionsCompat(options)
                .navigation(getActivity());
    }
}
