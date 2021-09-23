package com.knight.wanandroid.module_set.module_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.LanguageFontSizeUtils;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.module_entity.LanguageSelectEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/10 16:24
 * @descript:
 */
public final class SelectLanguageAdapter extends BaseQuickAdapter<LanguageSelectEntity, BaseViewHolder> {

    public SelectLanguageAdapter(@Nullable List<LanguageSelectEntity> data) {
        super(R.layout.set_select_language_item);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LanguageSelectEntity languageSelectEntity) {
        if (LanguageFontSizeUtils.isChinese()) {
            baseViewHolder.setText(R.id.set_tv_language_name,languageSelectEntity.getLanguageName());
        } else {
            baseViewHolder.setText(R.id.set_tv_language_name,languageSelectEntity.getEnglishName());
        }

        if (languageSelectEntity.isSelect()) {
            baseViewHolder.setVisible(R.id.set_iv_select_language,true);
            ((ImageView)baseViewHolder.getView(R.id.set_iv_select_language)).setColorFilter(CacheUtils.getThemeColor());
        } else {
            baseViewHolder.setVisible(R.id.set_iv_select_language,false);
        }

        if (languageSelectEntity.isShowLine()) {
            baseViewHolder.setVisible(R.id.set_tv_language_line,true);
        } else {
            baseViewHolder.setVisible(R.id.set_tv_language_line,false);
        }
    }
}
