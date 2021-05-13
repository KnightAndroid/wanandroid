package com.knight.wanandroid.module_hierachy.module_adapter;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_common.ApplicationProvider;
import com.knight.wanandroid.library_util.StringUtils;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyTabArticleEntity;

import org.jetbrains.annotations.NotNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/12 16:59
 * @descript:
 */
public class HierachyTabAdapter extends BaseMultiItemQuickAdapter<HierachyTabArticleEntity, BaseViewHolder> {

    public HierachyTabAdapter(){
        super(null);
        addItemType(AppConfig.ARTICLE_TEXT_TYPE, R.layout.base_text_item);
        addItemType(AppConfig.ARTICLE_PICTURE_TYPE,R.layout.base_article_item);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HierachyTabArticleEntity hierachyTabArticleEntity) {
        switch (baseViewHolder.getItemViewType()){
            case AppConfig.ARTICLE_TEXT_TYPE:
                //作者
                if (!TextUtils.isEmpty(hierachyTabArticleEntity.getAuthor())) {
                    baseViewHolder.setText(R.id.base_item_articleauthor,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),hierachyTabArticleEntity.getAuthor(),AppConfig.SEARCH_KEYWORD));
                } else {
                    baseViewHolder.setText(R.id.base_item_articleauthor,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),hierachyTabArticleEntity.getShareUser(),AppConfig.SEARCH_KEYWORD));
                }
                //一级分类
                if (!TextUtils.isEmpty(hierachyTabArticleEntity.getSuperChapterName()) || !TextUtils.isEmpty(hierachyTabArticleEntity.getChapterName())) {
                    baseViewHolder.setVisible(R.id.base_tv_articlesuperchaptername,true);
                    if (!TextUtils.isEmpty(hierachyTabArticleEntity.getSuperChapterName())) {
                        if (!TextUtils.isEmpty(hierachyTabArticleEntity.getChapterName())) {
                            baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,hierachyTabArticleEntity.getSuperChapterName() + "/" +hierachyTabArticleEntity.getChapterName());
                        } else {
                            baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,hierachyTabArticleEntity.getSuperChapterName());
                        }
                    } else {
                        if (!TextUtils.isEmpty(hierachyTabArticleEntity.getChapterName())) {
                            baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,hierachyTabArticleEntity.getChapterName());
                        } else {
                            baseViewHolder.setText(R.id.base_tv_articlesuperchaptername,"");
                        }
                    }
                } else {
                    baseViewHolder.setGone(R.id.base_tv_articlesuperchaptername,true);
                }

                //时间赋值
                if (!TextUtils.isEmpty(hierachyTabArticleEntity.getNiceDate())) {
                    baseViewHolder.setText(R.id.base_item_articledata,hierachyTabArticleEntity.getNiceDate());
                } else {
                    baseViewHolder.setText(R.id.base_item_articledata,hierachyTabArticleEntity.getNiceShareDate());
                }
                //标题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    baseViewHolder.setText(R.id.base_tv_articletitle, StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(hierachyTabArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY).toString(), AppConfig.SEARCH_KEYWORD));
                } else {
                    baseViewHolder.setText(R.id.base_tv_articletitle,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(hierachyTabArticleEntity.getTitle()).toString(),AppConfig.SEARCH_KEYWORD));
                }
                //是否收藏
                if (hierachyTabArticleEntity.isCollect()) {
                    baseViewHolder.setBackgroundResource(R.id.base_icon_collect,R.drawable.base_icon_collect);
                } else {
                    baseViewHolder.setBackgroundResource(R.id.base_icon_collect,R.drawable.base_icon_nocollect);
                }
                break;
            case AppConfig.ARTICLE_PICTURE_TYPE:
                //项目图片
                GlideEngineUtils.getInstance().loadStringPhoto(ApplicationProvider.getInstance().getApplication(),hierachyTabArticleEntity.getEnvelopePic(),baseViewHolder.getView(R.id.base_item_imageview));
                //作者
                if (!TextUtils.isEmpty(hierachyTabArticleEntity.getAuthor())) {
                    baseViewHolder.setText(R.id.base_item_tv_author,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),hierachyTabArticleEntity.getAuthor(),AppConfig.SEARCH_KEYWORD));
                } else {
                    baseViewHolder.setText(R.id.base_item_tv_author,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),hierachyTabArticleEntity.getShareUser(),AppConfig.SEARCH_KEYWORD));
                }

                //时间赋值
                if (!TextUtils.isEmpty(hierachyTabArticleEntity.getNiceDate())) {
                    baseViewHolder.setText(R.id.base_item_tv_time,hierachyTabArticleEntity.getNiceDate());
                } else {
                    baseViewHolder.setText(R.id.base_item_tv_time,hierachyTabArticleEntity.getNiceShareDate());
                }

                //标题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    baseViewHolder.setText(R.id.base_tv_title, StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(hierachyTabArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY).toString(), AppConfig.SEARCH_KEYWORD));
                } else {
                    baseViewHolder.setText(R.id.base_tv_title,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(hierachyTabArticleEntity.getTitle()).toString(),AppConfig.SEARCH_KEYWORD));
                }

                //描述
                if (!TextUtils.isEmpty(hierachyTabArticleEntity.getDesc())) {
                    baseViewHolder.setVisible(R.id.base_tv_project_desc,true);
                    //标题
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        baseViewHolder.setText(R.id.base_tv_project_desc, StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(), Html.fromHtml(hierachyTabArticleEntity.getDesc(),Html.FROM_HTML_MODE_LEGACY).toString(), AppConfig.SEARCH_KEYWORD));
                    } else {
                        baseViewHolder.setText(R.id.base_tv_project_desc,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(hierachyTabArticleEntity.getDesc()).toString(),AppConfig.SEARCH_KEYWORD));
                    }
                } else {
                    baseViewHolder.setGone(R.id.base_tv_project_desc,true);
                }

                //分类
                if (!TextUtils.isEmpty(hierachyTabArticleEntity.getSuperChapterName())) {
                    baseViewHolder.setVisible(R.id.base_tv_superchapter,true);
                    baseViewHolder.setText(R.id.base_tv_superchapter,hierachyTabArticleEntity.getSuperChapterName());
                } else {
                    baseViewHolder.setGone(R.id.base_tv_superchapter,true);
                }

                //是否收藏
                if (hierachyTabArticleEntity.isCollect()) {
                    baseViewHolder.setBackgroundResource(R.id.base_article_collect,R.drawable.base_icon_collect);
                } else {
                    baseViewHolder.setBackgroundResource(R.id.base_article_collect,R.drawable.base_icon_nocollect);
                }
                break;
            default:
                break;
        }


    }

}
