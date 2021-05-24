package com.knight.wanandroid.module_home.module_adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/24 11:18
 * @descript:
 */
public class TopArticleAroundAdapter extends BaseQuickAdapter<TopArticleEntity, BaseViewHolder> {


    private int selectItem;

    public void setSelectItem(int selectItem){
        this.selectItem = selectItem;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public TopArticleAroundAdapter(List<TopArticleEntity> topArticleEntities){
        super(R.layout.home_toparticle_around_item,topArticleEntities);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (getSelectItem() == position) {
            holder.itemView.findViewById(R.id.home_rl_toparticle).setScaleX(1.3f);
            holder.itemView.findViewById(R.id.home_rl_toparticle).setScaleY(1.3f);
        } else {
            holder.itemView.findViewById(R.id.home_rl_toparticle).setScaleX(1.0f);
            holder.itemView.findViewById(R.id.home_rl_toparticle).setScaleY(1.0f);
        }
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TopArticleEntity topArticleEntity) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#55aff4"));
        gradientDrawable.setShape(GradientDrawable.OVAL);
        baseViewHolder.itemView.findViewById(R.id.home_iv_toparticle).setBackground(gradientDrawable);
        baseViewHolder.setText(R.id.home_tv_toparticle_author,topArticleEntity.getAuthor().substring(0,1));

    }
}
