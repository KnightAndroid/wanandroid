package com.knight.wanandroid.module_set.module_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.LanguageFontSizeUtils;
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
public final class SelectDarkModeAdapter extends BaseQuickAdapter<DarkSelectEntity, BaseViewHolder> {



    public SelectDarkModeAdapter(@Nullable List<DarkSelectEntity> data) {
        super(R.layout.set_select_dark_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DarkSelectEntity darkSelectEntity) {
        if (LanguageFontSizeUtils.isChinese()) {
            baseViewHolder.setText(R.id.set_tv_dark_name,darkSelectEntity.getName());
        } else {
            baseViewHolder.setText(R.id.set_tv_dark_name,darkSelectEntity.getEnglishName());
        }

        if (darkSelectEntity.isSelect()) {
            baseViewHolder.setVisible(R.id.set_iv_select_darkmodel,true);
            ((ImageView)baseViewHolder.getView(R.id.set_iv_select_darkmodel)).setColorFilter(CacheUtils.getThemeColor());
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
