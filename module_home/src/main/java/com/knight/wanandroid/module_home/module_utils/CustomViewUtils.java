package com.knight.wanandroid.module_home.module_utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.knight.wanandroid.library_common.ApplicationProvider;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.module_home.module_constants.HomeConstants;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

import androidx.viewpager2.widget.ViewPager2;
import kotlin.jvm.functions.Function1;

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
    public static void bindViewPager2(MagicIndicator magicIndicator, ViewPager2 viewPager2, List<String> mDataList, Function1 action) {
       // magicIndicator.setBackgroundColor(Color.WHITE);
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
                        action.invoke(index);
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






}
