package com.knight.wanandroid.module_utils.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.module_utils.R;
import com.knight.wanandroid.module_utils.entity.UtilsEntity;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/12 14:32
 * Description:UtilItemAdapter
 */
public class UtilItemAdapter extends BaseQuickAdapter<UtilsEntity, BaseViewHolder> {

    public UtilItemAdapter(@Nullable List<UtilsEntity> data) {
        super(R.layout.utils_item, data);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, UtilsEntity utilsEntity) {
        baseViewHolder.setText(R.id.utils_title,utilsEntity.getTabName());
        baseViewHolder.setText(R.id.utils_desc,utilsEntity.getDesc());
        baseViewHolder.setTextColor(R.id.utils_title, CacheUtils.getThemeColor());
    }
}
