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
    private boolean mIsShowOnlyCount;
    private int mCount = 3;//设置最多展示几条数据
    public void setAnimation() {
        setAnimationEnable(true);
        this.setAnimationWithDefault(AnimationType.AlphaIn);

    }

    public TopArticleAdapter(List<TopArticleModel> topArticleModels) {
        super(R.layout.home_top_article_item, topArticleModels);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TopArticleModel topArticleModel) {
        //设置标题
        baseViewHolder.setText(R.id.home_tv_article_title, topArticleModel.getTitle());
        //设置作者
        baseViewHolder.setText(R.id.tv_article_author, topArticleModel.getAuthor());
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
