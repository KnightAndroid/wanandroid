package com.knight.wanandroid.module_home.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_home.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/5 17:10
 * @descript:
 */
public class MoreKnowLedgeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MoreKnowLedgeAdapter(@Nullable List<String> data){
        super(R.layout.home_knowledgelabel_item,data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
          baseViewHolder.setText(R.id.home_tv_knowledge,s);
    }
}
