package com.knight.wanandroid.module_course.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_course.R;
import com.knight.wanandroid.module_course.adapter.CourseListAdapter;
import com.knight.wanandroid.module_course.contract.CourseListContract;
import com.knight.wanandroid.module_course.databinding.CourseListActivityBinding;
import com.knight.wanandroid.module_course.entity.CourseEntity;
import com.knight.wanandroid.module_course.model.CourseListModel;
import com.knight.wanandroid.module_course.presenter.CourseListPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

@Route(path = RoutePathActivity.Course.CourseList_pager)
public class CourseListActivity extends BaseActivity<CourseListActivityBinding, CourseListPresenter, CourseListModel> implements CourseListContract.CourseListView, OnRefreshListener, OnLoadMoreListener {



    private CourseListAdapter mCourseListAdapter;


    @Override
    public int layoutId() {
        return R.layout.course_list_activity;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeCourseToolbar.baseTvTitle.setText(getString(R.string.course_titile));
        mDatabind.includeCourseToolbar.baseIvBack.setOnClickListener(v -> finish());
        mCourseListAdapter = new CourseListAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeCourseRv.baseBodyRv,new LinearLayoutManager(this),mCourseListAdapter,false);
        mDatabind.includeCourseRv.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeCourseRv.baseFreshlayout.setEnableLoadMore(false);
        showLoading(mDatabind.includeCourseRv.baseFreshlayout);
        initListener();
    }

    @Override
    public void initData(){
        mPresenter.requestCourseData();
    }


    @Override
    public void reLoadData(){
        mPresenter.requestCourseData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void setCourseData(List<CourseEntity> utilsData) {
        showSuccess();
        mDatabind.includeCourseRv.baseFreshlayout.finishRefresh();
        mCourseListAdapter.setNewInstance(utilsData);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestCourseData();
    }

    private void initListener(){
        mCourseListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //跳到享详情列表
                ARouter.getInstance().build(RoutePathActivity.Course.CourseDetailList_pager)
                          .withInt("cid",mCourseListAdapter.getData().get(position).getId()).navigation();
            }
        });
    }
}