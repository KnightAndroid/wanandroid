package com.knight.wanandroid.module_set.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.module_entity.DarkSelectEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/19 11:38
 * @descript:
 */
public class SelectDarkModeAdapter extends BaseQuickAdapter<DarkSelectEntity, BaseViewHolder> {



    public SelectDarkModeAdapter(@Nullable List<DarkSelectEntity> data) {
        super(R.layout.set_select_dark_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DarkSelectEntity darkSelectEntity) {
        baseViewHolder.setText(R.id.set_tv_dark_name,darkSelectEntity.getName());
        if (darkSelectEntity.isSelect()) {
            baseViewHolder.setVisible(R.id.set_iv_select_darkmodel,true);
        } else {
            baseViewHolder.setVisible(R.id.set_iv_select_darkmodel,false);
        }


        if (darkSelectEntity.isShowLine()) {
            baseViewHolder.setVisible(R.id.set_tv_darkmodel_line,true);
        } else {
            baseViewHolder.setVisible(R.id.set_tv_darkmodel_line,false);
        }

    }
}
