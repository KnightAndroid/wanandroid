package com.knight.wanandroid.module_course.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.knight.wanandroid.library_util.imageengine.ImageLoader;
import com.knight.wanandroid.module_course.R;
import com.knight.wanandroid.module_course.entity.CourseEntity;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/13 18:01
 * Description:CourseListAdapter
 */
public class CourseListAdapter extends BaseQuickAdapter<CourseEntity, BaseViewHolder> {

    public CourseListAdapter(@Nullable List<CourseEntity> data){
        super(R.layout.course_list_item,data);

    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, CourseEntity courseEntity) {
        ImageLoader.loadStringPhoto(getContext(),courseEntity.getCover(),baseViewHolder.getView(R.id.course_list_iv_cover));
        //作者
        if (!TextUtils.isEmpty(courseEntity.getAuthor())) {
            baseViewHolder.setText(R.id.course_list_tv_author,courseEntity.getAuthor());
        } else {
            baseViewHolder.setText(R.id.course_list_tv_author,"不详");
        }

        //标题
        baseViewHolder.setText(R.id.course_tv_title,courseEntity.getName());
        //描述
        baseViewHolder.setText(R.id.course_list_tv_desc,courseEntity.getDesc());

    }
}
