package com.knight.wanandroid.module_home.module_fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.fragment.BaseDBFragment;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_widget.pagertransformer.DragLayout;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentToptabsBinding;
import com.knight.wanandroid.module_home.module_activity.WebTransitionActivity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;

import java.io.Serializable;

import androidx.core.app.ActivityCompat;
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
    protected void initView(Bundle savedInstanceState) {
        mTopArticleEntity  = (TopArticleEntity) getArguments().getSerializable("topdata");
        mDatabind.homeToparticleDrawlayout.setGotoDetailListener(this);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(ColorUtils.getRandColorCode());
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




    }

    @Override
    public void gotoDetail() {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getContext(),new Pair(mDatabind.homeToparticleIv, AppConfig.IMAGE_TRANSITION_NAME));
        Intent intent = new Intent(getActivity(), WebTransitionActivity.class);
        ActivityCompat.startActivity(getActivity(),intent,options.toBundle());
       // ARouter.getInstance().build(RoutePathActivity.Web.Web_Transtiion).withOptionsCompat(options).navigation(getActivity());
    }
}
