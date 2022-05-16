package com.knight.wanandroid.module_course.adapter;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.module_course.R;
import com.knight.wanandroid.module_course.entity.CourseDetailEntity;
import com.knight.wanandroid.module_course.entity.CourseEntity;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/16 11:02
 * Description:CourseDetailListAdapter
 */
public class CourseDetailListAdapter extends BaseQuickAdapter<CourseDetailEntity, BaseViewHolder> {


    public CourseDetailListAdapter(@Nullable List<CourseDetailEntity> data){
        super(R.layout.course_detail_list_item,data);

    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, CourseDetailEntity courseDetailEntity) {
        baseViewHolder.setText(R.id.course_item_articleauthor,courseDetailEntity.getAuthor());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(2, CacheUtils.getThemeColor());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            baseViewHolder.getView(R.id.course_tv_articlechaptername).setBackground(gradientDrawable);
        } else {
            baseViewHolder.getView(R.id.course_tv_articlechaptername).setBackgroundDrawable(gradientDrawable);
        }
        //标签
        baseViewHolder.setText(R.id.course_tv_articlechaptername,courseDetailEntity.getChapterName());
        //标题
        baseViewHolder.setText(R.id.course_tv_articletitle,courseDetailEntity.getTitle());
        //时间
        baseViewHolder.setText(R.id.course_item_articledata,courseDetailEntity.getNiceDate());
    }
}
