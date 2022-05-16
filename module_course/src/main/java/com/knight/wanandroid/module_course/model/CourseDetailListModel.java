package com.knight.wanandroid.module_course.model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_course.contract.CourseDetailListContract;
import com.knight.wanandroid.module_course.entity.CourseDetailListEntity;
import com.knight.wanandroid.module_course.entity.CourseEntity;
import com.knight.wanandroid.module_course.request.CourseDetailListApi;
import com.knight.wanandroid.module_course.request.CourseListApi;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/16 11:25
 * Description:CourseDetailListModel
 */
public class CourseDetailListModel implements CourseDetailListContract.CourseDetailListModel {
    @Override
    public void requestDetailCourseData(BaseActivity activity, int page, int cid, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new CourseDetailListApi().setPage(page).setCid(cid))
                .request(new HttpCallback<HttpData<CourseDetailListEntity>>(activity){
                    @Override
                    public void onSucceed(HttpData<CourseDetailListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }
}
