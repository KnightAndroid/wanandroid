package com.knight.wanandroid.module_project.module_fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_common.utils.ColorUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_feedback.dialog.FeedBackDialog;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public final class ProjectViewpagerFragment extends BaseFragment<ProjectViewpagerFragmentBinding, ProjectArticlePresenter, ProjectArticleModel> implements ProjectArticleContract.ProjectArticleView, OnRefreshListener, OnLoadMoreListener {

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
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
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
        mDatabind.projectFloatBtn.setBackgroundTintList(ColorUtils.createColorStateList(CacheUtils.getThemeColor(),CacheUtils.getThemeColor()));

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
            if (projectArticle.getDatas().size() == 0) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void oncollectSuccess(EventBusUtils.CollectSuccess collectSuccess){
        onRefresh(mDatabind.projectListSmartfreshlayout);
    }

    /**
     * 登录成功
     * @param loginInSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginInSuccess(EventBusUtils.LoginInSuccess loginInSuccess) {
        //登录成功
        page = 0;
        mPresenter.requestProjectArticle(page,cid,isNewProject);
    }

    /**
     * 退出登录成功
     * @param logoutSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutSuccess(EventBusUtils.LogoutSuccess logoutSuccess){
        page = 0;
        mPresenter.requestProjectArticle(page,cid,isNewProject);
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

        mProjectArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mProjectArticleAdapter.getData().get(position).getLink(),
                        mProjectArticleAdapter.getData().get(position).getTitle(),
                        mProjectArticleAdapter.getData().get(position).getId(),
                        mProjectArticleAdapter.getData().get(position).isCollect(),
                        mProjectArticleAdapter.getData().get(position).getEnvelopePic(),
                        mProjectArticleAdapter.getData().get(position).getDesc(),
                        mProjectArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mProjectArticleAdapter.getData().get(position).getAuthor()) ? mProjectArticleAdapter.getData().get(position).getShareUser() : mProjectArticleAdapter.getData().get(position).getAuthor());
            }
        });

        mProjectArticleAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                FeedBackDialog.newInstance(mProjectArticleAdapter.getData().get(position).getId()).showAllowingStateLoss(getParentFragmentManager(),"feedbackDialog");
                return false;
            }
        });
    }

    public class ProcyClick{

        public void scrollTop(){
            mDatabind.projectListRv.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
