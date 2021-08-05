package com.knight.wanandroid.module_home.module_adapter;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/9 10:29
 * @descript:置顶文章
 */
public class TopArticleAdapter extends BaseQuickAdapter<TopArticleEntity, BaseViewHolder> {
    private boolean mIsShowOnlyCount;
    private int mCount = 3;//设置最多展示几条数据
    public void setAnimation() {
        setAnimationEnable(true);
        this.setAnimationWithDefault(AnimationType.AlphaIn);

    }

    public TopArticleAdapter(List<TopArticleEntity> topArticleEntities) {
        super(R.layout.home_top_article_item, topArticleEntities);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TopArticleEntity topArticleEntity) {
        //设置标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.home_tv_article_title, Html.fromHtml(topArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            baseViewHolder.setText(R.id.home_tv_article_title,Html.fromHtml(topArticleEntity.getTitle()));
        }

        if (!TextUtils.isEmpty(topArticleEntity.getAuthor())) {
            //设置作者
            baseViewHolder.setText(R.id.tv_article_author, topArticleEntity.getAuthor());
        } else {
            //设置作者
            baseViewHolder.setText(R.id.tv_article_author, topArticleEntity.getShareUser());
        }

    }

    /**
     * 设置是否仅显示的条数         * 默认全部显示
     */
    public void setShowOnlyThree(boolean isShowOnlyThree) {
        setShowOnlyCount(isShowOnlyThree, 3);
    }

    /**
     * 设置显示的条数
     */
    public void setShowOnlyCount(boolean isShowOnlyThree, int count) {
        mIsShowOnlyCount = isShowOnlyThree;
        mCount = count;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mIsShowOnlyCount ? super.getItemCount() > mCount ? mCount : super.getItemCount() : super.getItemCount();
    }
}
