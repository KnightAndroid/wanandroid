package com.knight.wanandroid.module_hierachy.module_fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.basefragment.BaseDBFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_base.util.InitCustomViewUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.databinding.HierachyNavigateMainFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/30 10:53
 * @descript:导航 体系
 */
@Route(path = RoutePathFragment.Hierachy.HierachyNavigate)
public class HierachyNavigateMainFragment extends BaseDBFragment<HierachyNavigateMainFragmentBinding> {


    //存放体系和导航两个Fragment
    List<Fragment> mFragments = new ArrayList<>();


    @Override
    protected int layoutId() {
        return R.layout.hierachy_navigate_main_fragment;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData(){
        List<String> mTitleList = new ArrayList<>();
        mTitleList.add("导航");
        mTitleList.add("体系");
        mFragments.clear();
        mFragments.add(new NavigateFragment());
        mFragments.add(new HierachyFragment());
        ViewSetUtils.setViewPager2Init(getActivity(), mFragments, mDatabind.hierachyNavigateViewpager, false);
        InitCustomViewUtils.bindViewPager2(mDatabind.hierachyNavigateIndicator, mDatabind.hierachyNavigateViewpager, mTitleList,null);
    }
}
