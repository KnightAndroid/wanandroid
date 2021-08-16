package com.knight.wanandroid.module_square.module_adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_common.provider.ApplicationProvider;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.StringUtils;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.module_entity.SquareQuestionEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/17 14:08
 * @descript:问答
 */
public class SquareQuestionAdapter extends BaseQuickAdapter<SquareQuestionEntity, BaseViewHolder> {

    public SquareQuestionAdapter(@Nullable List<SquareQuestionEntity> data){
        super(R.layout.base_text_item,data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SquareQuestionEntity squareQuestionEntity) {
        //作者
        if (!TextUtils.isEmpty(squareQuestionEntity.getAuthor())) {
            baseViewHolder.setText(R.id.base_item_articleauthor,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),squareQuestionEntity.getAuthor(),AppConfig.SEARCH_KEYWORD));
        } else {
            baseViewHolder.setText(R.id.base_item_articleauthor,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),squareQuestionEntity.getShareUser(),AppConfig.SEARCH_KEYWORD));
        }

        //一级分类
        if (squareQuestionEntity.getTags() != null && squareQuestionEntity.getTags().size() > 0) {
            baseViewHolder.setVisible(R.id.base_tv_articlesuperchaptername,true);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setStroke(2, CacheUtils.getInstance().getThemeColor());
            baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,squareQuestionEntity.getTags().get(0).getName());
            baseViewHolder.setTextColor(R.id.base_tv_articlesuperchaptername,CacheUtils.getInstance().getThemeColor());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                baseViewHolder.getView(R.id.base_tv_articlesuperchaptername).setBackground(gradientDrawable);
            } else {
                baseViewHolder.getView(R.id.base_tv_articlesuperchaptername).setBackgroundDrawable(gradientDrawable);
            }
        } else {
            baseViewHolder.setGone(R.id.base_tv_articlesuperchaptername,true);
        }

        //时间赋值
        if (!TextUtils.isEmpty(squareQuestionEntity.getNiceDate())) {
            baseViewHolder.setText(R.id.base_item_articledata,squareQuestionEntity.getNiceDate());
        } else {
            baseViewHolder.setText(R.id.base_item_articledata,squareQuestionEntity.getNiceShareDate());
        }
        //标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.base_tv_articletitle, StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(), Html.fromHtml(squareQuestionEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY).toString(), AppConfig.SEARCH_KEYWORD));
        } else {
            baseViewHolder.setText(R.id.base_tv_articletitle,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(squareQuestionEntity.getTitle()).toString(),AppConfig.SEARCH_KEYWORD));
        }

        //是否收藏
        if (squareQuestionEntity.isCollect()) {
            baseViewHolder.setBackgroundResource(R.id.base_icon_collect,R.drawable.base_icon_collect);
        } else {
            baseViewHolder.setBackgroundResource(R.id.base_icon_collect,R.drawable.base_icon_nocollect);
        }

    }
}
