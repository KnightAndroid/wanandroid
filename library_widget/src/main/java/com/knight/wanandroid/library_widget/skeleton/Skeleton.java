package com.knight.wanandroid.library_widget.skeleton;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/12/8 10:16
 * @descript:
 */
public class Skeleton {
    public static RecyclerViewSkeletonScreen.Builder bind(RecyclerView recyclerView) {
        return new RecyclerViewSkeletonScreen.Builder(recyclerView);
    }

    public static ViewSkeletonScreen.Builder bind(View view) {
        return new ViewSkeletonScreen.Builder(view);
    }
}
