package com.knight.wanandroid.module_mine.module_adapter;

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
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.module_entity.MyArticleEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 19:18
 * @descript:
 */
public final class OtherShareArticleAdapter extends BaseQuickAdapter<MyArticleEntity, BaseViewHolder> {

    public OtherShareArticleAdapter(@Nullable List<MyArticleEntity> data) {
        super(R.layout.base_text_item, data);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyArticleEntity myArticleEntity) {
        //作者
        if (!TextUtils.isEmpty(myArticleEntity.getAuthor())) {
            baseViewHolder.setText(R.id.base_item_articleauthor,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),myArticleEntity.getAuthor(),AppConfig.SEARCH_KEYWORD));
        } else {
            baseViewHolder.setText(R.id.base_item_articleauthor,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),myArticleEntity.getShareUser(),AppConfig.SEARCH_KEYWORD));
        }
        //一级分类
        if (!TextUtils.isEmpty(myArticleEntity.getSuperChapterName()) || !TextUtils.isEmpty(myArticleEntity.getChapterName())) {
            baseViewHolder.setVisible(R.id.base_tv_articlesuperchaptername,true);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setStroke(2, CacheUtils.getInstance().getThemeColor());
            if (!TextUtils.isEmpty(myArticleEntity.getSuperChapterName())) {
                if (!TextUtils.isEmpty(myArticleEntity.getChapterName())) {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,myArticleEntity.getSuperChapterName() + "/" +myArticleEntity.getChapterName());
                } else {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,myArticleEntity.getSuperChapterName());
                }
            } else {
                if (!TextUtils.isEmpty(myArticleEntity.getChapterName())) {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,myArticleEntity.getChapterName());
                } else {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,"");
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                baseViewHolder.getView(R.id.base_tv_articlesuperchaptername).setBackground(gradientDrawable);
            } else {
                baseViewHolder.getView(R.id.base_tv_articlesuperchaptername).setBackgroundDrawable(gradientDrawable);
            }
        } else {
            baseViewHolder.setGone(R.id.base_tv_articlesuperchaptername,true);
        }

        //时间赋值
        if (!TextUtils.isEmpty(myArticleEntity.getNiceDate())) {
            baseViewHolder.setText(R.id.base_item_articledata,myArticleEntity.getNiceDate());
        } else {
            baseViewHolder.setText(R.id.base_item_articledata,myArticleEntity.getNiceShareDate());
        }
        //标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.base_tv_articletitle, StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(), Html.fromHtml(myArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY).toString(), AppConfig.SEARCH_KEYWORD));
        } else {
            baseViewHolder.setText(R.id.base_tv_articletitle,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(myArticleEntity.getTitle()).toString(),AppConfig.SEARCH_KEYWORD));
        }
        //是否收藏
        if (myArticleEntity.isCollect()) {
            baseViewHolder.setBackgroundResource(R.id.base_icon_collect,R.drawable.base_icon_collect);
        } else {
            baseViewHolder.setBackgroundResource(R.id.base_icon_collect,R.drawable.base_icon_nocollect);
        }
    }
}
