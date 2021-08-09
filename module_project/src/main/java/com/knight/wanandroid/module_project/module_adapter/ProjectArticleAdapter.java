package com.knight.wanandroid.module_project.module_adapter;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.provider.ApplicationProvider;
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
        super(R.layout.base_article_item,data);

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectArticleEntity projectArticleEntity) {
        //项目图片
        GlideEngineUtils.getInstance().loadStringPhoto(ApplicationProvider.getInstance().getApplication(),projectArticleEntity.getEnvelopePic(),baseViewHolder.getView(R.id.base_item_imageview));
        //作者
        if (!TextUtils.isEmpty(projectArticleEntity.getAuthor())) {
            baseViewHolder.setText(R.id.base_item_tv_author,projectArticleEntity.getAuthor());
        } else {
            baseViewHolder.setText(R.id.base_item_tv_author,projectArticleEntity.getShareUser());
        }

        //时间赋值
        if (!TextUtils.isEmpty(projectArticleEntity.getNiceDate())) {
            baseViewHolder.setText(R.id.base_item_tv_time,projectArticleEntity.getNiceDate());
        } else {
            baseViewHolder.setText(R.id.base_item_tv_time,projectArticleEntity.getNiceShareDate());
        }

        //标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.base_tv_title, Html.fromHtml(projectArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            baseViewHolder.setText(R.id.base_tv_title,Html.fromHtml(projectArticleEntity.getTitle()));
        }

        //描述
        if (!TextUtils.isEmpty(projectArticleEntity.getDesc())) {
            baseViewHolder.setVisible(R.id.base_tv_project_desc,true);
            //标题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                baseViewHolder.setText(R.id.base_tv_project_desc, Html.fromHtml(projectArticleEntity.getDesc(),Html.FROM_HTML_MODE_LEGACY));
            } else {
                baseViewHolder.setText(R.id.base_tv_project_desc,Html.fromHtml(projectArticleEntity.getDesc()));
            }
        } else {
            baseViewHolder.setGone(R.id.base_tv_project_desc,true);
        }

        //分类
        if (!TextUtils.isEmpty(projectArticleEntity.getSuperChapterName())) {
            baseViewHolder.setVisible(R.id.base_tv_superchapter,true);
            baseViewHolder.setText(R.id.base_tv_superchapter,projectArticleEntity.getSuperChapterName());
        } else {
            baseViewHolder.setGone(R.id.base_tv_superchapter,true);
        }

        //是否收藏
        if (projectArticleEntity.isCollect()) {
            baseViewHolder.setBackgroundResource(R.id.base_article_collect,R.drawable.base_icon_collect);
        } else {
            baseViewHolder.setBackgroundResource(R.id.base_article_collect,R.drawable.base_icon_nocollect);
        }

    }
}
