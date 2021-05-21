package com.knight.wanandroid.library_util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/3/29 20:02
 * @descript:一些view的设置
 */
public class ViewSetUtils {


    /**
     *
     * 设置Adapter一些属性
     * @param activity
     * @param viewPager2
     * @param fragments
     * @param isUserInputEnabled
     */
    public static void setIsUserInputEnable(FragmentActivity activity, ViewPager2 viewPager2, final ArrayList<Fragment> fragments, boolean isUserInputEnabled){
        viewPager2.setUserInputEnabled(isUserInputEnabled);
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







    /**
     *
     * @param activity
     * @param fragments
     * @param viewPager2
     * @param isUserInputEnabled
     */
    public static void setViewPager2Init(FragmentActivity activity, List<Fragment> fragments, ViewPager2 viewPager2, boolean isUserInputEnabled) {
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



    public static CompositePageTransformer setViewPage2(ViewPager2 viewPage2,int multiWidth, int pageMargin){
        CompositePageTransformer mCompositePageTransformer;
        viewPage2.setPageTransformer(mCompositePageTransformer = new CompositePageTransformer());
        if (pageMargin < 0) {
            pageMargin = 0;
        }
        mCompositePageTransformer.addTransformer(new MarginPageTransformer(pageMargin));
        RecyclerView recyclerView = (RecyclerView) viewPage2.getChildAt(0);
        if (viewPage2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
            recyclerView.setPadding(viewPage2.getPaddingLeft(), multiWidth + Math.abs(pageMargin),viewPage2.getPaddingRight(), multiWidth + Math.abs(pageMargin));
        } else {
            recyclerView.setPadding(multiWidth + Math.abs(pageMargin), viewPage2.getPaddingTop(), multiWidth + Math.abs(pageMargin),viewPage2.getPaddingBottom());
        }
        recyclerView.setClipToPadding(false);

        return mCompositePageTransformer;

    }










}
