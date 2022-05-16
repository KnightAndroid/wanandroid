package com.knight.wanandroid.module_course.presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_course.contract.CourseListContract;
import com.knight.wanandroid.module_course.entity.CourseEntity;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/13 17:17
 * Description:CourseListPresenter
 */
public class CourseListPresenter extends CourseListContract.CourseListPresenter {
    @Override
    public void requestCourseData() {
        final CourseListContract.CourseListView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestCourseData((BaseActivity) mView,new MvpListener<List<CourseEntity>>(){
            @Override
            public void onSuccess(List<CourseEntity> data) {
                mView.setCourseData(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });

    }
}
