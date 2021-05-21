package com.knight.wanandroid.module_home.module_fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.knight.wanandroid.library_base.fragment.BaseDBFragment;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_widget.pagertransformer.DragLayout;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentToptabsBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/21 16:01
 * @descript:
 */
public class HomeTopTabsFragment extends BaseDBFragment<HomeFragmentToptabsBinding> implements DragLayout.GotoDetailListener {
    @Override
    protected int layoutId() {
        return R.layout.home_fragment_toptabs;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.homeToparticleDrawlayout.setGotoDetailListener(this);
        GradientDrawable gradientDrawable = new GradientDrawable();
       // gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(ColorUtils.getRandColorCode());
        mDatabind.homeToparticleIv.setBackground(gradientDrawable);
    }

    @Override
    public void gotoDetail() {

    }
}
