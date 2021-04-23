package com.knight.wanandroid.module_mine.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
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
public class UserRankCoinAdapter extends BaseQuickAdapter<CoinRankEntity, BaseViewHolder> {



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
                baseViewHolder.setTextColorRes(R.id.mine_tv_rank,R.color.base_color_theme);
                baseViewHolder.setTextColorRes(R.id.mine_tv_rankusername,R.color.base_color_theme);
            } else {
                baseViewHolder.setTextColorRes(R.id.mine_tv_rank,R.color.base_color_title);
                baseViewHolder.setTextColorRes(R.id.mine_tv_rankusername,R.color.base_color_title);
            }
        } else {
            baseViewHolder.setTextColorRes(R.id.mine_tv_rank,R.color.base_color_title);
            baseViewHolder.setTextColorRes(R.id.mine_tv_rankusername,R.color.base_color_title);
        }

    }
}
