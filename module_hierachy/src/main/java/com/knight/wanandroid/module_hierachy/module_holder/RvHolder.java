package com.knight.wanandroid.module_hierachy.module_holder;

import android.view.View;

import com.knight.wanandroid.module_hierachy.module_listener.RvListener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 16:11
 * @descript:
 */
public abstract class RvHolder<T> extends RecyclerView.ViewHolder {
    protected RvListener mListener;

    public RvHolder(View itemView, int type, RvListener listener) {
        super(itemView);
        this.mListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v.getId(), getAdapterPosition());
            }
        });
    }

    public abstract void bindHolder(T t, int position);

}
