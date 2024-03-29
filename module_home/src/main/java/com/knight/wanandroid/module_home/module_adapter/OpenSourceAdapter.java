package com.knight.wanandroid.module_home.module_adapter;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.module_entity.OpenSourceEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/31 15:26
 * @descript:
 */
public final class OpenSourceAdapter extends BaseQuickAdapter<OpenSourceEntity, BaseViewHolder> {


    public OpenSourceAdapter(List<OpenSourceEntity> openSourceEntities){
        super(R.layout.home_opensource_item,openSourceEntities);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OpenSourceEntity openSourceEntity) {
        baseViewHolder.setText(R.id.home_opensource_title,openSourceEntity.getName());
        baseViewHolder.setText(R.id.home_opensource_desc,openSourceEntity.getDesc());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((TextView)baseViewHolder.getView(R.id.home_opensource_abroadlink_value)).setText(Html.fromHtml("<u>"+openSourceEntity.getAbroadlink()+"</u>",Html.FROM_HTML_MODE_LEGACY));
        } else {
            ((TextView)baseViewHolder.getView(R.id.home_opensource_abroadlink_value)).setText(Html.fromHtml("<u>"+openSourceEntity.getAbroadlink()+"</u>"));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((TextView)baseViewHolder.getView(R.id.home_opensource_internallink_value)).setText(Html.fromHtml("<u>"+openSourceEntity.getInternallink()+"</u>",Html.FROM_HTML_MODE_LEGACY));
        } else {
            ((TextView)baseViewHolder.getView(R.id.home_opensource_internallink_value)).setText(Html.fromHtml("<u>"+openSourceEntity.getInternallink()+"</u>"));
        }

        baseViewHolder.setTextColor(R.id.home_opensource_title, CacheUtils.getThemeColor());
        baseViewHolder.setTextColor(R.id.home_opensource_abroadlink_value,CacheUtils.getThemeColor());
        baseViewHolder.setTextColor(R.id.home_opensource_internallink_value,CacheUtils.getThemeColor());

    }
}
