package com.knight.wanandroid.module_project.module_fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
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
    private boolean isNewProject;
    private ProjectArticleAdapter mProjectArticleAdapter;

    public static ProjectViewpagerFragment newInstance(int cid,boolean isNewProject){
        ProjectViewpagerFragment projectViewpagerFragment = new ProjectViewpagerFragment();
        Bundle args = new Bundle();
        args.putInt("cid",cid);
        args.putBoolean("isNewProject",isNewProject);
        projectViewpagerFragment.setArguments(args);
        return projectViewpagerFragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.project_viewpager_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        loadLoading(mDatabind.projectListSmartfreshlayout);
        cid = getArguments().getInt("cid");
        isNewProject = getArguments().getBoolean("isNewProject");
        if (isNewProject) {
            page = 0;
        } else {
            page = 1;
        }
        mDatabind.projectListSmartfreshlayout.setOnRefreshListener(this);
        mDatabind.projectListSmartfreshlayout.setOnLoadMoreListener(this);
        mProjectArticleAdapter = new ProjectArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.projectListRv,new LinearLayoutManager(getActivity()),mProjectArticleAdapter,true);
        initListener();
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestProjectArticle(page,cid,isNewProject);

    }

    @Override
    protected void reLoadData() {
        mPresenter.requestProjectArticle(page,cid,isNewProject);

    }

    @Override
    public void setProjectArticle(ProjectArticleListEntity projectArticle) {
        showSuccess();
        mDatabind.projectListSmartfreshlayout.finishRefresh();
        mDatabind.projectListSmartfreshlayout.finishLoadMore();
        if (projectArticle.getDatas().size() > 0) {
            if (projectArticle.getCurPage() == 1) {
                mProjectArticleAdapter.setNewInstance(projectArticle.getDatas());
            } else {
                mProjectArticleAdapter.addData(projectArticle.getDatas());
            }
            if (projectArticle.getDatas().size() < 10) {
                mDatabind.projectListSmartfreshlayout.setEnableLoadMore(false);
            } else {
                page++;
            }
        } else {
            mDatabind.projectListSmartfreshlayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void collectArticleSuccess(int position) {
        mProjectArticleAdapter.getData().get(position).setCollect(true);
        mProjectArticleAdapter.notifyItemChanged(position);
    }

    @Override
    public void cancelArticleSuccess(int position) {
        mProjectArticleAdapter.getData().get(position).setCollect(false);
        mProjectArticleAdapter.notifyItemChanged(position);
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
        mPresenter.requestProjectArticle(page,cid,isNewProject);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (isNewProject) {
            page = 0;
        } else {
            page = 1;
        }
        mDatabind.projectListSmartfreshlayout.setEnableLoadMore(true);
        mPresenter.requestProjectArticle(page,cid,isNewProject);
    }

    private void initListener(){
        mProjectArticleAdapter.addChildClickViewIds(R.id.base_article_collect);
        mProjectArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @LoginCheck
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.base_article_collect) {
                    if (mProjectArticleAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mProjectArticleAdapter.getData().get(position).getId(),position);
                    } else {
                        mPresenter.requestCollectArticle(mProjectArticleAdapter.getData().get(position).getId(),position);
                    }
                }
            }
        });
    }

    public class ProcyClick{

        public void scrollTop(){
            mDatabind.projectListRv.smoothScrollToPosition(0);
        }
    }


}
