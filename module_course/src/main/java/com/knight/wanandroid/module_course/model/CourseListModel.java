package com.knight.wanandroid.module_course.model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_course.contract.CourseListContract;
import com.knight.wanandroid.module_course.entity.CourseEntity;
import com.knight.wanandroid.module_course.request.CourseListApi;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/13 17:21
 * Description:CourseListModel
 */
public class CourseListModel implements CourseListContract.CourseListModel {
    @Override
    public void requestCourseData(BaseActivity activity, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new CourseListApi())
                .request(new HttpCallback<HttpData<List<CourseEntity>>>(activity){
                    @Override
                    public void onSucceed(HttpData<List<CourseEntity>> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }
}
