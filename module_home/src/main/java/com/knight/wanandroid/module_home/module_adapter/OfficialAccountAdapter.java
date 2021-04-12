package com.knight.wanandroid.module_home.module_adapter;

import android.graphics.drawable.GradientDrawable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.module_entity.OfficialAccountModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 15:14
 * @descript:公众号适配器
 */
public class OfficialAccountAdapter extends BaseQuickAdapter<OfficialAccountModel, BaseViewHolder> {

    public OfficialAccountAdapter(List<OfficialAccountModel> officialAccountModels){
        super(R.layout.home_official_accounts_item,officialAccountModels);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OfficialAccountModel officialAccountModel) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(ColorUtils.getRandColorCode());
        baseViewHolder.getView(R.id.home_iv_official_account).setBackground(gradientDrawable);
        baseViewHolder.setText(R.id.home_tv_officialaccount_name,officialAccountModel.getName());
        baseViewHolder.setText(R.id.tv_head_username,officialAccountModel.getName().substring(0,1));

    }
}
