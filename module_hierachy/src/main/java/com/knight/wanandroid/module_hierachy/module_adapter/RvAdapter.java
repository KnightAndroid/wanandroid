package com.knight.wanandroid.module_hierachy.module_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knight.wanandroid.module_hierachy.module_holder.RvHolder;
import com.knight.wanandroid.module_hierachy.module_listener.RvListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 16:10
 * @descript:
 */
public abstract class RvAdapter<T> extends RecyclerView.Adapter<RvHolder> {
    protected List<T> list;
    protected Context mContext;
    protected RvListener listener;
    protected LayoutInflater mInflater;
    private RecyclerView mRecyclerView;

    public RvAdapter(Context context, List<T> list, RvListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.listener = listener;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(getLayoutId(viewType), parent, false);
        return getHolder(view, viewType);
    }

    protected abstract int getLayoutId(int viewType);

    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        holder.bindHolder(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    protected abstract RvHolder getHolder(View view, int viewType);

}
