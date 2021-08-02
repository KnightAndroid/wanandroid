package com.knight.wanandroid.module_square.module_adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.module_entity.SquareArticleEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize knight
 * @Date 2021/4/25 19:43
 * @descript:
 */
public class SquareArticleAdapter extends BaseQuickAdapter<SquareArticleEntity, BaseViewHolder> {


    public SquareArticleAdapter(@Nullable List<SquareArticleEntity> data){
        super(R.layout.square_item_articles,data);

    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SquareArticleEntity squareArticleEntity) {
        //作者
        if (!TextUtils.isEmpty(squareArticleEntity.getAuthor())) {
            baseViewHolder.setText(R.id.square_item_articleauthor,squareArticleEntity.getAuthor());
        } else {
            baseViewHolder.setText(R.id.square_item_articleauthor,squareArticleEntity.getShareUser());
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(2,CacheUtils.getInstance().getThemeColor());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            baseViewHolder.getView(R.id.square_item_articlechaptername).setBackground(gradientDrawable);
        } else {
            baseViewHolder.getView(R.id.square_item_articlechaptername).setBackgroundDrawable(gradientDrawable);
        }

        //二级分类
        if (!TextUtils.isEmpty(squareArticleEntity.getChapterName())) {
            baseViewHolder.setVisible(R.id.square_item_articlechaptername,true);
            baseViewHolder.setText(R.id.square_item_articlechaptername,squareArticleEntity.getChapterName());
            baseViewHolder.setTextColor(R.id.square_item_articlechaptername,CacheUtils.getInstance().getThemeColor());
        } else {
            baseViewHolder.setGone(R.id.square_item_articlechaptername,true);
        }


        //时间赋值
        if (!TextUtils.isEmpty(squareArticleEntity.getNiceDate())) {
            baseViewHolder.setText(R.id.square_item_articledata,squareArticleEntity.getNiceDate());
        } else {
            baseViewHolder.setText(R.id.square_item_articledata,squareArticleEntity.getNiceDate());
        }

        //标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.square_tv_articletitle, Html.fromHtml(squareArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            baseViewHolder.setText(R.id.square_tv_articletitle,Html.fromHtml(squareArticleEntity.getTitle()));
        }

        if (!CacheUtils.getInstance().getNormalDark()) {
            baseViewHolder.setTextColor(R.id.square_tv_articletitle,CacheUtils.getInstance().getTextColor());
        } else {
            baseViewHolder.setTextColor(R.id.square_tv_articletitle,R.color.base_color_title);
        }

        if (squareArticleEntity.isCollect()) {
            baseViewHolder.setBackgroundResource(R.id.square_icon_collect,R.drawable.base_icon_collect);
        } else {
            baseViewHolder.setBackgroundResource(R.id.square_icon_collect,R.drawable.base_icon_nocollect);
        }

        //是否是新文章
        if (squareArticleEntity.isFresh()) {
            baseViewHolder.setVisible(R.id.square_item_articlenew,true);
        } else {
            baseViewHolder.setGone(R.id.square_item_articlenew,true);
        }


    }
}
