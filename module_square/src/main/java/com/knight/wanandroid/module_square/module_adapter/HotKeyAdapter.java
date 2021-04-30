package com.knight.wanandroid.module_square.module_adapter;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.module_entity.SearchHotKeyEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/25 15:01
 * @descript:热词适配器
 */
public class HotKeyAdapter extends BaseQuickAdapter<SearchHotKeyEntity, BaseViewHolder> {



    public HotKeyAdapter(@Nullable List<SearchHotKeyEntity> data) {
        super(R.layout.square_item_hotsearch, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SearchHotKeyEntity searchHotKeyEntity) {
        baseViewHolder.setText(R.id.square_tv_hotkey,searchHotKeyEntity.getName());
    }


    @Override
    public void onBindViewHolder(@NotNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams lp = holder.getView(R.id.square_tv_hotkey).getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp =
                    (FlexboxLayoutManager.LayoutParams) holder.getView(R.id.square_tv_hotkey).getLayoutParams();
            flexboxLp.setFlexGrow(1.0f);
            flexboxLp.setAlignSelf(AlignItems.FLEX_END);
        }
    }








}
