package com.knight.wanandroid.module_course.contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_course.entity.CourseEntity;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/13 16:59
 * Description:CourseListContract
 */
public interface CourseListContract {

    interface CourseListView extends BaseView {


        //设置教程列表
        void setCourseData(List<CourseEntity> utilsData);
    }


    interface CourseListModel extends BaseModel {

        //请求教程列表
        void requestCourseData(BaseActivity activity, MvpListener mvpListener);
    }

    abstract class CourseListPresenter extends BasePresenter<CourseListModel, CourseListView> {

        public abstract void requestCourseData();
    }

}
