package com.knight.wanandroid.module_mine.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.module_entity.UserDetailCoinEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 11:26
 * @descript:积分明细适配器
 */
public final class UserDetailCoinAdapter extends BaseQuickAdapter<UserDetailCoinEntity, BaseViewHolder> {


    public UserDetailCoinAdapter(@Nullable List<UserDetailCoinEntity> data) {
        super(R.layout.mine_item_coindetail, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, UserDetailCoinEntity userDetailCoinEntity) {
        baseViewHolder.setText(R.id.mine_tv_detailpointtitle,userDetailCoinEntity.getReason());
        baseViewHolder.setText(R.id.mine_tv_coincount,"+"+userDetailCoinEntity.getCoinCount());
        baseViewHolder.setTextColor(R.id.mine_tv_coincount, CacheUtils.getInstance().getThemeColor());
        baseViewHolder.setText(R.id.mine_tv_timereason,userDetailCoinEntity.getDesc());
    }
}
