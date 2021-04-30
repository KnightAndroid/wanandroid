package com.knight.wanandroid.module_hierachy.module_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.module_holder.RvHolder;
import com.knight.wanandroid.module_hierachy.module_listener.RvListener;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 16:13
 * @descript:
 */
public class HierachyLeftBarAdapter extends RvAdapter<String> {
    private int checkedPosition;

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public HierachyLeftBarAdapter(Context context, List<String> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.hierachy_item_right_sidebar;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SortHolder(view, viewType, listener);
    }

    private class SortHolder extends RvHolder<String> {

        private TextView tvName;
        private TextView hierachy_tv_selectview;
        private View mView;

        SortHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            this.mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.hierachy_tv_sort);
            hierachy_tv_selectview = (TextView)itemView.findViewById(R.id.hierachy_tv_selectview);
        }

        @Override
        public void bindHolder(String string, int position) {
            tvName.setText(string);
            if (position == checkedPosition) {
                mView.setBackgroundColor(Color.parseColor("#f3f3f3"));
                tvName.setTextColor(Color.parseColor("#55aff4"));
                hierachy_tv_selectview.setVisibility(View.VISIBLE);
            } else {
                mView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvName.setTextColor(Color.parseColor("#333333"));
                hierachy_tv_selectview.setVisibility(View.INVISIBLE);
            }
        }

    }
}
