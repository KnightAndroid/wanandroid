package com.knight.wanandroid.module_home.module_adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
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
public final class HomeArticleAdapter extends BaseQuickAdapter<HomeArticleEntity, BaseViewHolder> {


    public HomeArticleAdapter(@Nullable List<HomeArticleEntity> data) {
        super(R.layout.home_article_item, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeArticleEntity homeArticleEntity) {
        //作者
        if (!TextUtils.isEmpty(homeArticleEntity.getAuthor())) {
            baseViewHolder.setText(R.id.home_item_articleauthor,homeArticleEntity.getAuthor());
        } else {
            baseViewHolder.setText(R.id.home_item_articleauthor,homeArticleEntity.getShareUser());
        }

        //是否是新文章
        if (homeArticleEntity.isFresh()) {
            baseViewHolder.setVisible(R.id.home_item_articlenew,true);
        } else {
            baseViewHolder.setGone(R.id.home_item_articlenew,true);
        }


        //二级分类
        if (!TextUtils.isEmpty(homeArticleEntity.getChapterName())) {
            baseViewHolder.setVisible(R.id.home_item_articlechaptername,true);
            baseViewHolder.setText(R.id.home_item_articlechaptername,homeArticleEntity.getChapterName());
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setStroke(2, CacheUtils.getInstance().getThemeColor());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                baseViewHolder.getView(R.id.home_item_articlechaptername).setBackground(gradientDrawable);
            } else {
                baseViewHolder.getView(R.id.home_item_articlechaptername).setBackgroundDrawable(gradientDrawable);
            }
            baseViewHolder.setTextColor(R.id.home_item_articlechaptername,CacheUtils.getInstance().getThemeColor());
        } else {
            baseViewHolder.setGone(R.id.home_item_articlechaptername,true);
        }

        //时间赋值
        if (!TextUtils.isEmpty(homeArticleEntity.getNiceDate())) {
            baseViewHolder.setText(R.id.home_item_articledata,homeArticleEntity.getNiceDate());
        } else {
            baseViewHolder.setText(R.id.home_item_articledata,homeArticleEntity.getNiceShareDate());
        }

        //标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.home_tv_articletitle, Html.fromHtml(homeArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            baseViewHolder.setText(R.id.home_tv_articletitle,Html.fromHtml(homeArticleEntity.getTitle()));
        }

        //描述
        if (!TextUtils.isEmpty(homeArticleEntity.getDesc())) {
            baseViewHolder.setVisible(R.id.home_tv_articledesc,true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                baseViewHolder.setText(R.id.home_tv_articledesc, Html.fromHtml(homeArticleEntity.getDesc(),Html.FROM_HTML_MODE_LEGACY));
            } else {
                baseViewHolder.setText(R.id.home_tv_articledesc,Html.fromHtml(homeArticleEntity.getDesc()));
            }
        } else {
            baseViewHolder.setGone(R.id.home_tv_articledesc,true);
        }

        //一级分类
        if (!TextUtils.isEmpty(homeArticleEntity.getSuperChapterName())) {
            baseViewHolder.setVisible(R.id.home_tv_articlesuperchaptername,true);
            baseViewHolder.setText(R.id.home_tv_articlesuperchaptername,homeArticleEntity.getSuperChapterName());
        } else {
            baseViewHolder.setGone(R.id.home_tv_articlesuperchaptername,true);
        }

        //是否收藏
        if (homeArticleEntity.isCollect()) {
            baseViewHolder.setBackgroundResource(R.id.home_icon_collect,R.drawable.base_icon_collect);
        } else {
            baseViewHolder.setBackgroundResource(R.id.home_icon_collect,R.drawable.base_icon_nocollect);
        }
    }
}
