package com.knight.wanandroid.module_mine.module_adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.module_entity.MyArticleEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/8 17:31
 * @descript:我的分享文章适配器
 */
public final class MyShareArticleAdapter extends BaseQuickAdapter<MyArticleEntity, BaseViewHolder> {

    public MyShareArticleAdapter(@Nullable List<MyArticleEntity> data) {
        super(R.layout.mine_item_sharearticle, data);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyArticleEntity myArticleEntity) {
        //作者
        baseViewHolder.setText(R.id.mine_item_articleauthor,myArticleEntity.getShareUser());
        //时间
        baseViewHolder.setText(R.id.mine_item_articledata,myArticleEntity.getNiceDate());
        //标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.mine_tv_articletitle, Html.fromHtml(myArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            baseViewHolder.setText(R.id.mine_tv_articletitle,Html.fromHtml(myArticleEntity.getTitle()));
        }


        //文章类别
        if (TextUtils.isEmpty(myArticleEntity.getChapterName())) {
            baseViewHolder.setText(R.id.mine_item_articlechaptername,myArticleEntity.getSuperChapterName());
        } else {
            baseViewHolder.setText(R.id.mine_item_articlechaptername,myArticleEntity.getSuperChapterName() + "/" +myArticleEntity.getChapterName());
        }

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(2, CacheUtils.getInstance().getThemeColor());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            baseViewHolder.getView(R.id.mine_item_articlechaptername).setBackground(gradientDrawable);
        } else {
            baseViewHolder.getView(R.id.mine_item_articlechaptername).setBackgroundDrawable(gradientDrawable);
        }
        baseViewHolder.setTextColor(R.id.mine_item_articlechaptername,CacheUtils.getInstance().getThemeColor());

        //是否是新文章
        if (myArticleEntity.isFresh()) {
            baseViewHolder.setVisible(R.id.mine_item_articlenew,true);
        } else {
            baseViewHolder.setGone(R.id.mine_item_articlenew,true);
        }

    }



}
