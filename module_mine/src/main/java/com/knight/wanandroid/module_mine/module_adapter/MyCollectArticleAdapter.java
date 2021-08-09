package com.knight.wanandroid.module_mine.module_adapter;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_common.provider.ApplicationProvider;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.module_entity.MyCollectArticleEntity;

import org.jetbrains.annotations.NotNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 17:41
 * @descript:
 */
public class MyCollectArticleAdapter extends BaseMultiItemQuickAdapter<MyCollectArticleEntity, BaseViewHolder> {

    public MyCollectArticleAdapter() {
        super(null);
        addItemType(AppConfig.ARTICLE_TEXT_TYPE, R.layout.base_text_item);
        addItemType(AppConfig.ARTICLE_PICTURE_TYPE, R.layout.base_article_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyCollectArticleEntity myCollectArticleEntity) {
        switch (baseViewHolder.getItemViewType()) {
            case AppConfig.ARTICLE_TEXT_TYPE:
                //作者
                if (!TextUtils.isEmpty(myCollectArticleEntity.getAuthor())) {
                    baseViewHolder.setText(R.id.base_item_articleauthor, myCollectArticleEntity.getAuthor());
                } else {
                    baseViewHolder.setText(R.id.base_item_articleauthor, "");
                }
                //一级分类
                if (!TextUtils.isEmpty(myCollectArticleEntity.getChapterName())) {
                    baseViewHolder.setVisible(R.id.base_tv_articlesuperchaptername, true);
                    baseViewHolder.setText(R.id.base_tv_articlesuperchaptername, myCollectArticleEntity.getChapterName());
                } else {
                    baseViewHolder.setGone(R.id.base_tv_articlesuperchaptername, true);
                }

                //时间赋值
                if (!TextUtils.isEmpty(myCollectArticleEntity.getNiceDate())) {
                    baseViewHolder.setText(R.id.base_item_articledata, myCollectArticleEntity.getNiceDate());
                } else {
                    baseViewHolder.setGone(R.id.base_item_articledata, true);
                }
                //标题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    baseViewHolder.setText(R.id.base_tv_articletitle, Html.fromHtml(myCollectArticleEntity.getTitle(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    baseViewHolder.setText(R.id.base_tv_articletitle, Html.fromHtml(myCollectArticleEntity.getTitle()));
                }
                //是否收藏

                baseViewHolder.setBackgroundResource(R.id.base_icon_collect, R.drawable.mine_icon_delete);

                break;
            case AppConfig.ARTICLE_PICTURE_TYPE:
                //项目图片
                GlideEngineUtils.getInstance().loadStringPhoto(ApplicationProvider.getInstance().getApplication(), myCollectArticleEntity.getEnvelopePic(), baseViewHolder.getView(R.id.base_item_imageview));
                //作者
                if (!TextUtils.isEmpty(myCollectArticleEntity.getAuthor())) {
                    baseViewHolder.setText(R.id.base_item_tv_author, myCollectArticleEntity.getAuthor());
                } else {
                    baseViewHolder.setText(R.id.base_item_tv_author, "");
                }

                //时间赋值
                if (!TextUtils.isEmpty(myCollectArticleEntity.getNiceDate())) {
                    baseViewHolder.setVisible(R.id.base_item_tv_time, true);
                    baseViewHolder.setText(R.id.base_item_tv_time, myCollectArticleEntity.getNiceDate());
                } else {
                    baseViewHolder.setGone(R.id.base_item_tv_time, true);
                }

                //标题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    baseViewHolder.setText(R.id.base_tv_title, Html.fromHtml(myCollectArticleEntity.getTitle(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    baseViewHolder.setText(R.id.base_tv_title, Html.fromHtml(myCollectArticleEntity.getTitle()));
                }

                //描述
                if (!TextUtils.isEmpty(myCollectArticleEntity.getDesc())) {
                    baseViewHolder.setVisible(R.id.base_tv_project_desc, true);
                    //标题
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        baseViewHolder.setText(R.id.base_tv_project_desc, Html.fromHtml(myCollectArticleEntity.getDesc(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        baseViewHolder.setText(R.id.base_tv_project_desc, Html.fromHtml(myCollectArticleEntity.getDesc()));
                    }
                } else {
                    baseViewHolder.setGone(R.id.base_tv_project_desc, true);
                }

                //分类
                if (!TextUtils.isEmpty(myCollectArticleEntity.getChapterName())) {
                    baseViewHolder.setVisible(R.id.base_tv_superchapter, true);
                    baseViewHolder.setText(R.id.base_tv_superchapter, myCollectArticleEntity.getChapterName());
                } else {
                    baseViewHolder.setGone(R.id.base_tv_superchapter, true);
                }

                baseViewHolder.setBackgroundResource(R.id.base_article_collect, R.drawable.mine_icon_delete);
                break;
            default:
                break;
        }


    }

}
