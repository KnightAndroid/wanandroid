package com.knight.wanandroid.module_home.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.module_home.R;
import com.wanandroid.knight.library_database.entity.SearchHistroyKeywordEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author created by knight
 * @organize 搜索记录适配器
 * @Date 2021/5/10 15:02
 * @descript:
 */
public class SearchRecordAdapter extends BaseQuickAdapter<SearchHistroyKeywordEntity, BaseViewHolder> {

    public SearchRecordAdapter(@Nullable List<SearchHistroyKeywordEntity> data){
        super(R.layout.home_searchkeyword_item,data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SearchHistroyKeywordEntity searchHistroyKeywordEntity) {
        baseViewHolder.setText(R.id.tv_search_keyword,searchHistroyKeywordEntity.getName());
    }
}
