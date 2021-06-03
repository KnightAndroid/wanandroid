package com.knight.wanandroid.module_mine.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.module_entity.VersionRecordEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/1 15:09
 * @descript:
 */
public class VersionRecordAdapter extends BaseQuickAdapter<VersionRecordEntity, BaseViewHolder> {

    public VersionRecordAdapter(@Nullable List<VersionRecordEntity> data){
        super(R.layout.mine_item_versionrecord,data);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, VersionRecordEntity versionRecordEntity) {
        baseViewHolder.setText(R.id.mine_appupdate_title,versionRecordEntity.getTitle());
        baseViewHolder.setText(R.id.mine_appupdate_tv_time,versionRecordEntity.getPublishTime());
        baseViewHolder.setText(R.id.mine_appupdate_desc,versionRecordEntity.getDesc());
    }
}
