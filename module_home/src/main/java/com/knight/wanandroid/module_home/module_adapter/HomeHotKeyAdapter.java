package com.knight.wanandroid.module_home.module_adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.module_home.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 17:06
 * @descript:
 */
public class HomeHotKeyAdapter extends BaseQuickAdapter<SearchHotKeyEntity, BaseViewHolder> {



    public HomeHotKeyAdapter(@Nullable List<SearchHotKeyEntity> data){
        super(R.layout.home_hotsearch_item,data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SearchHotKeyEntity searchHotKeyEntity) {
        baseViewHolder.setText(R.id.home_tv_hotkey,searchHotKeyEntity.getName());
        baseViewHolder.setTextColor(R.id.home_tv_hotkey,CacheUtils.getInstance().getThemeColor());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(2, CacheUtils.getInstance().getThemeColor());
        gradientDrawable.setCornerRadius(ScreenUtils.dp2px(6f));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            baseViewHolder.getView(R.id.home_tv_hotkey).setBackground(gradientDrawable);
        } else {
            baseViewHolder.getView(R.id.home_tv_hotkey).setBackgroundDrawable(gradientDrawable);
        }
    }

    @Override
    public void onBindViewHolder(@NotNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        ViewGroup.LayoutParams lp = holder.getView(R.id.home_tv_hotkey).getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp =
                    (FlexboxLayoutManager.LayoutParams) holder.getView(R.id.home_tv_hotkey).getLayoutParams();
            flexboxLp.setFlexGrow(1.0f);
            flexboxLp.setAlignSelf(AlignItems.FLEX_END);
        }
    }
}
