package com.knight.wanandroid.module_home.module_utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.knight.wanandroid.library_common.ApplicationProvider;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.module_home.module_constants.HomeConstants;
import com.knight.wanandroid.module_home.module_fragment.HomeArticlesFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:03
 * @descript:一些view的初始化
 */
public class CustomViewUtils {

    /**
     *
     * 设置导航栏绑定Viewpager2
     * @param magicIndicator
     * @param viewPager2
     * @param mDataList
     */
    public static void bindViewPager2(MagicIndicator magicIndicator,ViewPager2 viewPager2, List<String> mDataList) {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(ApplicationProvider.getInstance().getApplication());
        commonNavigator.setLeftPadding(ScreenUtils.dp2px(16));
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeConstants.ARTICLE_TYPE = mDataList.get(index);
                        viewPager2.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
               // indicator.setHorizontalPadding(10);
                indicator.setRoundRadius(ScreenUtils.dp2px(6));
                indicator.setFillColor(Color.parseColor("#55aff4"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                magicIndicator.onPageScrollStateChanged(state);
            }
        });

    }

    /**
     *
     *
     *
     * @param activity
     * @param fragments
     * @param viewPager2
     * @param isUserInputEnabled
     */
    public static void setViewPager2Init(FragmentActivity activity, List<HomeArticlesFragment> fragments, ViewPager2 viewPager2, boolean isUserInputEnabled) {
        viewPager2.setUserInputEnabled(isUserInputEnabled);
        //暂时解决切换白屏问题
        viewPager2.setOffscreenPageLimit(fragments.size());
        viewPager2.setAdapter(new FragmentStateAdapter(activity) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
    }

}
