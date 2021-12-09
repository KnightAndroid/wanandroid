package com.knight.wanandroid.library_widget.skeleton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knight.wanandroid.library_widget.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/12/8 10:19
 * @descript:
 */
public class ShimmerViewHolder extends RecyclerView.ViewHolder {

    public ShimmerViewHolder(LayoutInflater inflater, ViewGroup parent, int innerViewResId) {
        super(inflater.inflate(R.layout.layout_shimmer, parent, false));
        ViewGroup layout = (ViewGroup) itemView;
        View view = inflater.inflate(innerViewResId, layout, false);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null) {
            layout.setLayoutParams(lp);
        }
        layout.addView(view);
    }
}