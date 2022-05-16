package com.knight.wanandroid.module_course.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_course.R;
import com.knight.wanandroid.module_course.adapter.CourseDetailListAdapter;
import com.knight.wanandroid.module_course.contract.CourseDetailListContract;
import com.knight.wanandroid.module_course.databinding.CourseDetailListActivityBinding;
import com.knight.wanandroid.module_course.entity.CourseDetailListEntity;
import com.knight.wanandroid.module_course.model.CourseDetailListModel;
import com.knight.wanandroid.module_course.presenter.CourseDetailListPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Author:Knight
 * Time:2022/5/16 10:37
 * Description:CourseDetailListActivity
 */

@Route(path = RoutePathActivity.Course.CourseDetailList_pager)
public class CourseDetailListActivity extends BaseActivity<CourseDetailListActivityBinding, CourseDetailListPresenter, CourseDetailListModel> implements CourseDetailListContract.CourseDetailListView, OnRefreshListener, OnLoadMoreListener {


    @Autowired(name = "cid")
    int cid = 0;

    private int page = 0;

    private CourseDetailListAdapter mCourseDetailListAdapter;

    @Override
    public int layoutId() {
        return R.layout.course_detail_list_activity;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mDatabind.includeCourseDetailToolbar.baseTvTitle.setText(getString(R.string.course_detail_title));
        mDatabind.includeCourseDetailToolbar.baseIvBack.setOnClickListener(v -> finish());
        mCourseDetailListAdapter = new CourseDetailListAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeCourseDetailRv.baseBodyRv,new LinearLayoutManager(this),mCourseDetailListAdapter,false);
        mDatabind.includeCourseDetailRv.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeCourseDetailRv.baseFreshlayout.setOnLoadMoreListener(this);
        showLoading(mDatabind.includeCourseDetailRv.baseFreshlayout);
        initListener();
    }

    @Override
    public void initData(){
        mPresenter.requestDetailCourseData(page,cid);
    }


    @Override
    public void reLoadData(){
        mPresenter.requestDetailCourseData(page,cid);
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.show(errorMsg);
        showFailure();
    }

    @Override
    public void setCourseDetailData(CourseDetailListEntity courseDetailListEntity) {
        showSuccess();
        mDatabind.includeCourseDetailRv.baseFreshlayout.finishRefresh();
        mDatabind.includeCourseDetailRv.baseFreshlayout.finishLoadMore();
        if (courseDetailListEntity.getDatas().size() > 0) {
            if (page == 0) {
                mCourseDetailListAdapter.setNewInstance(courseDetailListEntity.getDatas());
            } else {
                mCourseDetailListAdapter.addData(courseDetailListEntity.getDatas());
            }
            if (courseDetailListEntity.getDatas().size() == 0) {
                mDatabind.includeCourseDetailRv.baseFreshlayout.setEnableLoadMore(false);
            } else {
                page++;
            }
        } else {
            mDatabind.includeCourseDetailRv.baseFreshlayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDetailCourseData(page,cid);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        mPresenter.requestDetailCourseData(page,cid);
    }

    private void initListener(){
        mCourseDetailListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                        .withString("webUrl",mCourseDetailListAdapter.getData().get(position).getLink())
                        .withString("webTitle",mCourseDetailListAdapter.getData().get(position).getTitle())
                        .navigation();
            }
        });
    }
}
