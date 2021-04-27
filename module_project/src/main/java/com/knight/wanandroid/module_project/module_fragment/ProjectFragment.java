package com.knight.wanandroid.module_project.module_fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_project.R;
import com.knight.wanandroid.module_project.databinding.ProjectFragmentBinding;
import com.knight.wanandroid.module_project.module_contract.ProjectContract;
import com.knight.wanandroid.module_project.module_entity.ProjectTypeEntity;
import com.knight.wanandroid.module_project.module_model.ProjectModel;
import com.knight.wanandroid.module_project.module_presenter.ProjectPresenter;
import com.knight.wanandroid.module_project.module_utils.ProjectInitViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/26 19:52
 * @descript:项目页面
 */
@Route(path = RoutePathFragment.Project.Project_Pager)
public class ProjectFragment extends BaseFragment<ProjectFragmentBinding, ProjectPresenter, ProjectModel> implements ProjectContract.ProjectView {

    List<ProjectViewpagerFragment> mFragments = new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.project_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadLoading(mDatabind.projectLlArticle);

    }

    @Override
    protected void reLoadData() {
        mPresenter.requestProjectTypes((BaseDBActivity) getActivity());
    }

    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.requestProjectTypes((BaseDBActivity) getActivity());

    }

    @Override
    public void setProjectTypes(List<ProjectTypeEntity> projectTypes) {
        showSuccess();
        mFragments.clear();
        for (int i = 0;i < projectTypes.size();i++) {
            mFragments.add(ProjectViewpagerFragment.newInstance(projectTypes.get(i).getId()));

        }
        ProjectInitViewUtils.setViewPager2InitFragment(this,mFragments,mDatabind.projectViewPager,false);
        ProjectInitViewUtils.bindProjectViewPager2(mDatabind.projectIndicator,mDatabind.projectViewPager,projectTypes);
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
}
