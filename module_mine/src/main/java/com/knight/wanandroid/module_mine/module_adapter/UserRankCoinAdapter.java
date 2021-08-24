package com.knight.wanandroid.module_mine.module_adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.module_entity.CoinRankEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 17:00
 * @descript:积分排行榜
 */
public final class UserRankCoinAdapter extends BaseQuickAdapter<CoinRankEntity, BaseViewHolder> {



    public UserRankCoinAdapter(@Nullable List<CoinRankEntity> data) {
        super(R.layout.mine_item_coinrank, data);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoinRankEntity coinRankEntity) {
        baseViewHolder.setText(R.id.mine_tv_rank,coinRankEntity.getRank());
        baseViewHolder.setText(R.id.mine_tv_rankusername,coinRankEntity.getUsername());
        baseViewHolder.setText(R.id.mine_tv_rankcoincount,coinRankEntity.getCoinCount()+"");
        if(ModuleConfig.getInstance().user != null){
            if(ModuleConfig.getInstance().user.getId() == coinRankEntity.getUserId()){
                baseViewHolder.setTextColor(R.id.mine_tv_rank, CacheUtils.getInstance().getThemeColor());
                baseViewHolder.setTextColor(R.id.mine_tv_rankusername,CacheUtils.getInstance().getThemeColor());
            } else {
                if (CacheUtils.getInstance().getNormalDark()) {
                    baseViewHolder.setTextColor(R.id.mine_tv_rank, Color.parseColor("#D3D3D3"));
                    baseViewHolder.setTextColor(R.id.mine_tv_rankusername,Color.parseColor("#D3D3D3"));
                } else {
                    baseViewHolder.setTextColor(R.id.mine_tv_rank,Color.parseColor("#333333"));
                    baseViewHolder.setTextColor(R.id.mine_tv_rankusername,Color.parseColor("#333333"));
                }

            }
        } else {
            if (CacheUtils.getInstance().getNormalDark()) {
                baseViewHolder.setTextColor(R.id.mine_tv_rank, Color.parseColor("#D3D3D3"));
                baseViewHolder.setTextColor(R.id.mine_tv_rankusername,Color.parseColor("#D3D3D3"));
            } else {
                baseViewHolder.setTextColor(R.id.mine_tv_rank,Color.parseColor("#333333"));
                baseViewHolder.setTextColor(R.id.mine_tv_rankusername,Color.parseColor("#333333"));
            }
        }
        baseViewHolder.setTextColor(R.id.mine_tv_rankcoincount,CacheUtils.getInstance().getThemeColor());
    }
}
