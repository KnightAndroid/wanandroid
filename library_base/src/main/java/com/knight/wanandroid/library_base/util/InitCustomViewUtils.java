package com.knight.wanandroid.library_base.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.knight.wanandroid.library_base.view.ScaleTransitionPagerTitleView;
import com.knight.wanandroid.library_common.provider.ApplicationProvider;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.ScreenUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import kotlin.jvm.functions.Function1;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/14 16:09
 * @descript:
 */
public class InitCustomViewUtils {

    /**
     *
     * 设置导航栏绑定Viewpager2
     * @param magicIndicator
     * @param viewPager2
     * @param mDataList
     */
    public static void bindViewPager2(MagicIndicator magicIndicator, ViewPager2 viewPager2, List<String> mDataList, @Nullable final Function1 action) {
        CommonNavigator commonNavigator = new CommonNavigator(ApplicationProvider.getInstance().getApplication());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView scaleTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                if(mDataList.size() != 0){
                    scaleTransitionPagerTitleView.setText(mDataList.get(index));
                }

                scaleTransitionPagerTitleView.setTextSize(18f);
                scaleTransitionPagerTitleView.setTextScaleX(CacheUtils.getInstance().getSystemFontSize());
                if (CacheUtils.getInstance().getNormalDark()) {
                    scaleTransitionPagerTitleView.setNormalColor(Color.parseColor("#D3D3D3"));
                    scaleTransitionPagerTitleView.setSelectedColor(Color.parseColor("#D3D3D3"));
                } else {
                    scaleTransitionPagerTitleView.setNormalColor(CacheUtils.getInstance().getThemeColor());
                    scaleTransitionPagerTitleView.setSelectedColor(CacheUtils.getInstance().getThemeColor());
                }

                scaleTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager2.setCurrentItem(index);
                        if (action != null) {
                            action.invoke(index);
                        }

                    }
                });
                return scaleTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(ScreenUtils.dp2px(context,3.0f));
                indicator.setLineWidth(ScreenUtils.dp2px(context,30.0f));
                indicator.setRoundRadius(ScreenUtils.dp2px(context,6.0f));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(CacheUtils.getInstance().getThemeColor());
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
     * @param fragment
     * @param fragments
     * @param viewPager2
     * @param isUserInputEnabled
     */
    public static void setViewPager2InitFragment(Fragment fragment, List<Fragment> fragments, ViewPager2 viewPager2, boolean isUserInputEnabled) {
        viewPager2.setUserInputEnabled(isUserInputEnabled);
//        viewPager2.setOffscreenPageLimit(fragments.size());
        viewPager2.setSaveEnabled(true);
        viewPager2.setAdapter(new FragmentStateAdapter(fragment) {
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
