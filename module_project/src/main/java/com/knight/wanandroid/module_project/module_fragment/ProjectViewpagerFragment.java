package com.knight.wanandroid.module_project.module_fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_project.R;
import com.knight.wanandroid.module_project.databinding.ProjectViewpagerFragmentBinding;
import com.knight.wanandroid.module_project.module_adapter.ProjectArticleAdapter;
import com.knight.wanandroid.module_project.module_contract.ProjectArticleContract;
import com.knight.wanandroid.module_project.module_entity.ProjectArticleListEntity;
import com.knight.wanandroid.module_project.module_model.ProjectArticleModel;
import com.knight.wanandroid.module_project.module_presenter.ProjectArticlePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/27 15:48
 * @descript:
 */
@Route(path = RoutePathFragment.Project.Project_Child_Pager)
public class ProjectViewpagerFragment extends BaseFragment<ProjectViewpagerFragmentBinding, ProjectArticlePresenter, ProjectArticleModel> implements ProjectArticleContract.ProjectArticleView, OnRefreshListener, OnLoadMoreListener {

    private int page = 1;
    private int cid;
    private ProjectArticleAdapter mProjectArticleAdapter;

    public static ProjectViewpagerFragment newInstance(int cid){
        ProjectViewpagerFragment projectViewpagerFragment = new ProjectViewpagerFragment();
        Bundle args = new Bundle();
        args.putInt("cid",cid);
        projectViewpagerFragment.setArguments(args);
        return projectViewpagerFragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.project_viewpager_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadLoading(mDatabind.projectListSmartfreshlayout);
        cid = getArguments().getInt("cid");
        mDatabind.projectListSmartfreshlayout.setOnRefreshListener(this);
        mDatabind.projectListSmartfreshlayout.setOnLoadMoreListener(this);
        mProjectArticleAdapter = new ProjectArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.projectListRv,new LinearLayoutManager(getActivity()),mProjectArticleAdapter,true);

    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestProjectArticle((BaseDBActivity)getActivity(),page,cid);

    }

    @Override
    protected void reLoadData() {
        mPresenter.requestProjectArticle((BaseDBActivity)getActivity(),page,cid);

    }

    @Override
    public void setProjectArticle(ProjectArticleListEntity projectArticle) {
        showSuccess();
        mDatabind.projectListSmartfreshlayout.finishRefresh();
        mDatabind.projectListSmartfreshlayout.finishLoadMore();
        if (projectArticle.getDatas().size() > 0) {
            if (page == 1) {
                mProjectArticleAdapter.setNewInstance(projectArticle.getDatas());
            } else {
                mProjectArticleAdapter.addData(projectArticle.getDatas());
            }
            page++;
        } else {
            mDatabind.projectListSmartfreshlayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestProjectArticle((BaseDBActivity)getActivity(),page,cid);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mDatabind.projectListSmartfreshlayout.setEnableLoadMore(true);
        mPresenter.requestProjectArticle((BaseDBActivity)getActivity(),page,cid);
    }
}
