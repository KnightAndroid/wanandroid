package com.knight.wanandroid.library_widget;

import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 16:38
 * @descript:设置view属性
 */
public class SetInitCustomView {

    /**
     *
     * 设置swipeRecycleview
     * @param swipeRecyclerView
     * @param layoutManger
     * @param bindAdapter
     * @param isScroll
     * @return
     */
    public static void initSwipeRecycleview(SwipeRecyclerView swipeRecyclerView,RecyclerView.LayoutManager layoutManger,RecyclerView.Adapter<?> bindAdapter,boolean isScroll) {
        swipeRecyclerView.setLayoutManager(layoutManger);
        swipeRecyclerView.setHasFixedSize(true);
        swipeRecyclerView.setAdapter(bindAdapter);
        swipeRecyclerView.setNestedScrollingEnabled(isScroll);

    }
}
