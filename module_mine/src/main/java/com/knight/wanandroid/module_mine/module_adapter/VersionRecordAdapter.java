package com.knight.wanandroid.module_mine.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.module_entity.AppUpdateRecordEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/1 15:09
 * @descript:
 */
public class VersionRecordAdapter extends BaseQuickAdapter<AppUpdateRecordEntity, BaseViewHolder> {

    public VersionRecordAdapter(@Nullable List<AppUpdateRecordEntity> data){
        super(R.layout.mine_item_versionrecord,data);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AppUpdateRecordEntity appUpdateRecordEntity) {
        baseViewHolder.setText(R.id.mine_appupdate_title,appUpdateRecordEntity.getTitle());
        baseViewHolder.setText(R.id.mine_appupdate_tv_time,appUpdateRecordEntity.getPublishTime());
        baseViewHolder.setText(R.id.mine_appupdate_desc,appUpdateRecordEntity.getDesc());
    }
}
