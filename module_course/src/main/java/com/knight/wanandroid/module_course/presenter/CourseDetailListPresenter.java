package com.knight.wanandroid.module_course.presenter;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_course.contract.CourseDetailListContract;
import com.knight.wanandroid.module_course.contract.CourseListContract;
import com.knight.wanandroid.module_course.entity.CourseDetailListEntity;
import com.knight.wanandroid.module_course.entity.CourseEntity;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/16 11:28
 * Description:CourseDetailListPresenter
 */
public class CourseDetailListPresenter extends CourseDetailListContract.CourseDetailListPresenter {
    @Override
    public void requestDetailCourseData(int page, int cid) {
        final CourseDetailListContract.CourseDetailListView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestDetailCourseData((BaseActivity) mView, page, cid, new MvpListener<CourseDetailListEntity>() {
            @Override
            public void onSuccess(CourseDetailListEntity data) {
                mView.setCourseDetailData(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
