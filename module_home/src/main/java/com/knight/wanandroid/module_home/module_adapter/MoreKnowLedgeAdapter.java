package com.knight.wanandroid.module_home.module_adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_widget.flowlayout.TagInfo;
import com.knight.wanandroid.module_home.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/5 17:10
 * @descript:
 */
public final class MoreKnowLedgeAdapter extends BaseQuickAdapter<TagInfo, BaseViewHolder> {


    private boolean isEdit;

    public MoreKnowLedgeAdapter(@Nullable List<TagInfo> data) {
        super(R.layout.home_knowledgelabel_item, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TagInfo tagInfo) {
        baseViewHolder.setText(R.id.home_tv_knowledge, tagInfo.tagName);
        baseViewHolder.setTextColor(R.id.home_tv_knowledge,CacheUtils.getInstance().getThemeColor());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(2, CacheUtils.getInstance().getThemeColor());
        gradientDrawable.setCornerRadius(ScreenUtils.dp2px(6f));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            baseViewHolder.getView(R.id.home_tv_knowledge).setBackground(gradientDrawable);
        } else {
            baseViewHolder.getView(R.id.home_tv_knowledge).setBackgroundDrawable(gradientDrawable);
        }
        //如果是编辑状态
        if (isEdit) {
            baseViewHolder.setVisible(R.id.home_iv_moreknowledge_delete, true);
        } else {
            baseViewHolder.setGone(R.id.home_iv_moreknowledge_delete, true);
        }


    }


    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    public boolean getIsEdit() {
        return isEdit;
    }


}
