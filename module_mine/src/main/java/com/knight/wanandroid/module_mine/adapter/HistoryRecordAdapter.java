package com.knight.wanandroid.module_mine.adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.DateUtils;
import com.knight.wanandroid.module_mine.R;
import com.wanandroid.knight.library_database.entity.HistoryReadRecordsEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/8 13:59
 * @descript:阅读历史适配器
 */
public final class HistoryRecordAdapter extends BaseQuickAdapter<HistoryReadRecordsEntity, BaseViewHolder> {
    public HistoryRecordAdapter(@Nullable List<HistoryReadRecordsEntity> data) {
        super(R.layout.base_text_item, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HistoryReadRecordsEntity historyReadRecordsEntity) {
        //作者
        if (!TextUtils.isEmpty(historyReadRecordsEntity.getAuthor())) {
            baseViewHolder.setText(R.id.base_item_articleauthor, historyReadRecordsEntity.getAuthor());
        } else {
            baseViewHolder.setText(R.id.base_item_articleauthor, "");
        }
        //一级分类
        if (!TextUtils.isEmpty(historyReadRecordsEntity.getChapterName())) {
            baseViewHolder.setVisible(R.id.base_tv_articlesuperchaptername, true);
            baseViewHolder.setText(R.id.base_tv_articlesuperchaptername, historyReadRecordsEntity.getChapterName());
            baseViewHolder.setTextColor(R.id.base_tv_articlesuperchaptername,CacheUtils.getThemeColor());
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setStroke(2, CacheUtils.getThemeColor());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                baseViewHolder.getView(R.id.base_tv_articlesuperchaptername).setBackground(gradientDrawable);
            } else {
                baseViewHolder.getView(R.id.base_tv_articlesuperchaptername).setBackgroundDrawable(gradientDrawable);
            }

        } else {
            baseViewHolder.setGone(R.id.base_tv_articlesuperchaptername, true);
        }

        //时间赋值
        if (historyReadRecordsEntity.getInsertTime() != null) {
            baseViewHolder.setText(R.id.base_item_articledata,DateUtils.ConvertYearMonthDayTime(historyReadRecordsEntity.getInsertTime()));
        } else {
            baseViewHolder.setText(R.id.base_item_articledata,"");
        }
        //标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.base_tv_articletitle, Html.fromHtml(historyReadRecordsEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY).toString());
        } else {
            baseViewHolder.setText(R.id.base_tv_articletitle,Html.fromHtml(historyReadRecordsEntity.getTitle()).toString());
        }


        baseViewHolder.setGone(R.id.base_icon_collect,true);

    }
}
