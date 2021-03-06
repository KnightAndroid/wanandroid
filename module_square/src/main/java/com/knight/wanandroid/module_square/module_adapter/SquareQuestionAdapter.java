package com.knight.wanandroid.module_square.module_adapter;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_common.ApplicationProvider;
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

        //标识
        if (squareQuestionEntity.getTags() != null && squareQuestionEntity.getTags().size() > 0) {
            baseViewHolder.setVisible(R.id.base_item_articlechaptername,true);
            baseViewHolder.setText(R.id.base_item_articlechaptername,squareQuestionEntity.getTags().get(0).getName());
        } else {
            baseViewHolder.setGone(R.id.base_item_articlechaptername,true);
        }


        //一级分类
        if (!TextUtils.isEmpty(squareQuestionEntity.getSuperChapterName()) || !TextUtils.isEmpty(squareQuestionEntity.getChapterName())) {
            baseViewHolder.setVisible(R.id.base_tv_articlesuperchaptername,true);
            if (!TextUtils.isEmpty(squareQuestionEntity.getSuperChapterName())) {
                if (!TextUtils.isEmpty(squareQuestionEntity.getChapterName())) {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,squareQuestionEntity.getSuperChapterName() + "/" +squareQuestionEntity.getChapterName());
                } else {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,squareQuestionEntity.getSuperChapterName());
                }
            } else {
                if (!TextUtils.isEmpty(squareQuestionEntity.getChapterName())) {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,squareQuestionEntity.getChapterName());
                } else {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,"");
                }
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
