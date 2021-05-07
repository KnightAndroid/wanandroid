package com.knight.wanandroid.module_home.module_adapter;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.ApplicationProvider;
import com.knight.wanandroid.library_util.StringUtils;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.module_constants.HomeConstants;
import com.knight.wanandroid.module_home.module_entity.HomeArticleEntity;

import org.jetbrains.annotations.NotNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 19:20
 * @descript:
 */
public class SearchResultAdapter extends BaseMultiItemQuickAdapter<HomeArticleEntity, BaseViewHolder> {

    public SearchResultAdapter(){
        super(null);
        addItemType(HomeConstants.ARTICLE_TEXT_TYPE,R.layout.home_searchresult_item);
        addItemType(HomeConstants.ARTICLE_PICTURE_TYPE,R.layout.base_article_item);
    }

   // public SearchResultAdapter(@Nullable List<HomeArticleEntity> data) {
     //   super(R.layout.home_searchresult_item, data);
    //}

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeArticleEntity homeArticleEntity) {
        switch (baseViewHolder.getItemViewType()){
            case HomeConstants.ARTICLE_TEXT_TYPE:
                //作者
                if (!TextUtils.isEmpty(homeArticleEntity.getAuthor())) {
                    baseViewHolder.setText(R.id.home_search_item_articleauthor,homeArticleEntity.getAuthor());
                } else {
                    baseViewHolder.setText(R.id.home_search_item_articleauthor,homeArticleEntity.getShareUser());
                }
                //一级分类
                if (!TextUtils.isEmpty(homeArticleEntity.getSuperChapterName()) || !TextUtils.isEmpty(homeArticleEntity.getChapterName())) {
                    baseViewHolder.setVisible(R.id.home_search_tv_articlesuperchaptername,true);
                    if (!TextUtils.isEmpty(homeArticleEntity.getSuperChapterName())) {
                        if (!TextUtils.isEmpty(homeArticleEntity.getChapterName())) {
                            baseViewHolder.setText(R.id.home_search_tv_articlesuperchaptername,homeArticleEntity.getSuperChapterName() + "/" +homeArticleEntity.getChapterName());
                        } else {
                            baseViewHolder.setText(R.id.home_search_tv_articlesuperchaptername,homeArticleEntity.getSuperChapterName());
                        }
                    } else {
                        if (!TextUtils.isEmpty(homeArticleEntity.getChapterName())) {
                            baseViewHolder.setText(R.id.home_search_tv_articlesuperchaptername,homeArticleEntity.getChapterName());
                        } else {
                            baseViewHolder.setText(R.id.home_search_tv_articlesuperchaptername,"");
                        }
                    }
                } else {
                    baseViewHolder.setGone(R.id.home_search_tv_articlesuperchaptername,true);
                }

                //时间赋值
                if (!TextUtils.isEmpty(homeArticleEntity.getNiceDate())) {
                    baseViewHolder.setText(R.id.home_search_item_articledata,homeArticleEntity.getNiceDate());
                } else {
                    baseViewHolder.setText(R.id.home_search_item_articledata,homeArticleEntity.getNiceShareDate());
                }
                //标题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    baseViewHolder.setText(R.id.home_search_tv_articletitle, StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(homeArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY).toString(), HomeConstants.SEARCH_KEYWORD));
                } else {
                    baseViewHolder.setText(R.id.home_search_tv_articletitle,StringUtils.getStyle(ApplicationProvider.getInstance().getApplication(),Html.fromHtml(homeArticleEntity.getTitle()).toString(),HomeConstants.SEARCH_KEYWORD));
                }
                //是否收藏
                if (homeArticleEntity.isCollect()) {
                    baseViewHolder.setBackgroundResource(R.id.home_search_icon_collect,R.drawable.base_icon_collect);
                } else {
                    baseViewHolder.setBackgroundResource(R.id.home_search_icon_collect,R.drawable.base_icon_nocollect);
                }
                break;
            case HomeConstants.ARTICLE_PICTURE_TYPE:
                //项目图片
                GlideEngineUtils.getInstance().loadStringPhoto(ApplicationProvider.getInstance().getApplication(),homeArticleEntity.getEnvelopePic(),baseViewHolder.getView(R.id.base_item_imageview));
                //作者
                if (!TextUtils.isEmpty(homeArticleEntity.getAuthor())) {
                    baseViewHolder.setText(R.id.base_item_tv_author,homeArticleEntity.getAuthor());
                } else {
                    baseViewHolder.setText(R.id.base_item_tv_author,homeArticleEntity.getShareUser());
                }

                //时间赋值
                if (!TextUtils.isEmpty(homeArticleEntity.getNiceDate())) {
                    baseViewHolder.setText(R.id.base_item_tv_time,homeArticleEntity.getNiceDate());
                } else {
                    baseViewHolder.setText(R.id.base_item_tv_time,homeArticleEntity.getNiceShareDate());
                }

                //标题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    baseViewHolder.setText(R.id.base_tv_title, Html.fromHtml(homeArticleEntity.getTitle(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    baseViewHolder.setText(R.id.base_tv_title,Html.fromHtml(homeArticleEntity.getTitle()));
                }

                //描述
                if (!TextUtils.isEmpty(homeArticleEntity.getDesc())) {
                    baseViewHolder.setVisible(R.id.base_tv_project_desc,true);
                    //标题
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        baseViewHolder.setText(R.id.base_tv_project_desc, Html.fromHtml(homeArticleEntity.getDesc(),Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        baseViewHolder.setText(R.id.base_tv_project_desc,Html.fromHtml(homeArticleEntity.getDesc()));
                    }
                } else {
                    baseViewHolder.setGone(R.id.base_tv_project_desc,true);
                }

                //分类
                if (!TextUtils.isEmpty(homeArticleEntity.getSuperChapterName())) {
                    baseViewHolder.setVisible(R.id.base_tv_superchapter,true);
                    baseViewHolder.setText(R.id.base_tv_superchapter,homeArticleEntity.getSuperChapterName());
                } else {
                    baseViewHolder.setGone(R.id.base_tv_superchapter,true);
                }

                //是否收藏
                if (homeArticleEntity.isCollect()) {
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
