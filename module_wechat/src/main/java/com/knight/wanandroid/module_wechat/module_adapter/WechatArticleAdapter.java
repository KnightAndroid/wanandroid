package com.knight.wanandroid.module_wechat.module_adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.module_wechat.R;
import com.knight.wanandroid.module_wechat.module_entity.WechatArticleEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/14 16:19
 * @descript:
 */
public class WechatArticleAdapter extends BaseQuickAdapter<WechatArticleEntity, BaseViewHolder> {

    public WechatArticleAdapter(@Nullable List<WechatArticleEntity> data){
        super(R.layout.base_text_item,data);

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WechatArticleEntity wechatArticleEntity) {
        //作者
        if (!TextUtils.isEmpty(wechatArticleEntity.getAuthor())) {
            baseViewHolder.setText(R.id.base_item_articleauthor,wechatArticleEntity.getAuthor());
        } else {
            baseViewHolder.setText(R.id.base_item_articleauthor,wechatArticleEntity.getShareUser());
        }
        //一级分类
        if (!TextUtils.isEmpty(wechatArticleEntity.getSuperChapterName()) || !TextUtils.isEmpty(wechatArticleEntity.getChapterName())) {
            baseViewHolder.setVisible(R.id.base_tv_articlesuperchaptername,true);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setStroke(2, CacheUtils.getInstance().getThemeColor());
            if (!TextUtils.isEmpty(wechatArticleEntity.getSuperChapterName())) {
                if (!TextUtils.isEmpty(wechatArticleEntity.getChapterName())) {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,wechatArticleEntity.getSuperChapterName() + "/" +wechatArticleEntity.getChapterName());
                } else {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,wechatArticleEntity.getSuperChapterName());
                }
            } else {
                if (!TextUtils.isEmpty(wechatArticleEntity.getChapterName())) {
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,wechatArticleEntity.getChapterName());
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
        if (!TextUtils.isEmpty(wechatArticleEntity.getNiceDate())) {
            baseViewHolder.setText(R.id.base_item_articledata,wechatArticleEntity.getNiceDate());
        } else {
            baseViewHolder.setText(R.id.base_item_articledata,wechatArticleEntity.getNiceShareDate());
        }
        //标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.base_tv_articletitle, wechatArticleEntity.getTitle());
        } else {
            baseViewHolder.setText(R.id.base_tv_articletitle,wechatArticleEntity.getTitle());
        }
        //是否收藏
        if (wechatArticleEntity.isCollect()) {
            baseViewHolder.setBackgroundResource(R.id.base_icon_collect,R.drawable.base_icon_collect);
        } else {
            baseViewHolder.setBackgroundResource(R.id.base_icon_collect,R.drawable.base_icon_nocollect);
        }

    }
}
