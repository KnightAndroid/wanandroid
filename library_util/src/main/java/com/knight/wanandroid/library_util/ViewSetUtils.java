package com.knight.wanandroid.library_util;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
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
