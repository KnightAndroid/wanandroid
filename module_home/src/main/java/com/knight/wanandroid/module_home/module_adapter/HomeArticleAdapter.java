package com.knight.wanandroid.module_home.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.module_entity.HomeArticleEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 20:11
 * @descript:
 */
public class HomeArticleAdapter extends BaseQuickAdapter<HomeArticleEntity, BaseViewHolder> {


    public HomeArticleAdapter(@Nullable List<HomeArticleEntity> data) {
        super(R.layout.home_article_item, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeArticleEntity homeArticleEntity) {
        baseViewHolder.setText(R.id.tv_home_article_title,homeArticleEntity.getTitle());
    }
}
