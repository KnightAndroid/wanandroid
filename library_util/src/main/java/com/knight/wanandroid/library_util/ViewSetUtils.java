package com.knight.wanandroid.library_util;

import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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


    /**
     *
     * @param activity
     * @param fragments
     * @param viewPager2
     * @param isUserInputEnabled
     */
    public static void setHomeViewPager2Init(FragmentActivity activity, List<Fragment> fragments, List<Integer> mFragmentHashCodes,ViewPager2 viewPager2, boolean isUserInputEnabled) {
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

            @Override
            public long getItemId(int position){
                return fragments.get(position).hashCode();

            }

            @Override
            public boolean containsItem(long itemId) {
                return mFragmentHashCodes.contains(itemId);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void avoidHintColor (View view){
        if (view instanceof TextView) {
            ((TextView) view).setHighlightColor(Color.parseColor("#00000000"));
            
        }
    }


    /**
     *
     * 监听文字输入
     * @param editText
     * @param tv
     */
    public static void setLinstenerInputNumber(EditText editText,TextView tv){
        editText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv.setText(temp.toString().trim().length() + "/30");
            }
        });

    }










}
