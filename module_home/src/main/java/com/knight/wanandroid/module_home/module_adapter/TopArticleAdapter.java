package com.knight.wanandroid.module_home.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.module_entity.TopArticleModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 10:29
 * @descript:置顶文章
 */
public class TopArticleAdapter extends BaseQuickAdapter<TopArticleModel, BaseViewHolder> {

    public TopArticleAdapter(List<TopArticleModel> topArticleModels) {
        super(R.layout.home_top_article_item,topArticleModels);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TopArticleModel topArticleModel) {
        //设置标题
        baseViewHolder.setText(R.id.home_tv_article_title,topArticleModel.getTitle());
        //设置作者
        baseViewHolder.setText(R.id.tv_article_author,topArticleModel.getAuthor());
    }
}
