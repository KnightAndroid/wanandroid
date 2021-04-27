package com.knight.wanandroid.module_project.module_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.ApplicationProvider;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.module_project.R;
import com.knight.wanandroid.module_project.module_entity.ProjectArticleEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author created by 项目文章列表
 * @organize knight
 * @Date 2021/4/27 19:22
 * @descript:
 */
public class ProjectArticleAdapter extends BaseQuickAdapter<ProjectArticleEntity, BaseViewHolder> {


    public ProjectArticleAdapter(@Nullable List<ProjectArticleEntity> data){
        super(R.layout.project_article_item,data);

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectArticleEntity projectArticleEntity) {
        GlideEngineUtils.getInstance().loadStringPhoto(ApplicationProvider.getInstance().getApplication(),projectArticleEntity.getEnvelopePic(),baseViewHolder.getView(R.id.item_project_imageview));
    }
}
