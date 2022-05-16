package com.knight.wanandroid.module_course.contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_course.entity.CourseDetailListEntity;
import com.knight.wanandroid.module_course.entity.CourseEntity;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/16 11:11
 * Description:CourseDetailListCOntract
 */
public interface CourseDetailListContract {
    interface CourseDetailListView extends BaseView {


        //设置教程详细列表
        void setCourseDetailData(CourseDetailListEntity courseDetailListEntity);
    }


    interface CourseDetailListModel extends BaseModel {

        //请求教程详细列表
        void requestDetailCourseData(BaseActivity activity,int page,int cid,MvpListener mvpListener);
    }

    abstract class CourseDetailListPresenter extends BasePresenter<CourseDetailListModel, CourseDetailListView> {
        //请求教程详细列表
        public abstract void requestDetailCourseData(int page,int cid);
    }
}
