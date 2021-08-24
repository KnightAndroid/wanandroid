package com.knight.wanandroid.module_project.module_presenter;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_project.module_contract.ProjectContract;
import com.knight.wanandroid.module_project.module_entity.ProjectTypeEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/26 20:06
 * @descript:项目逻辑层
 */
public final class ProjectPresenter extends ProjectContract.ProjectDataPresenter {
    @Override
    public void requestProjectTypes() {
        final ProjectContract.ProjectView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestProjectTypes((BaseFragment)mView, new MvpListener<List<ProjectTypeEntity>>() {
            @Override
            public void onSuccess(List<ProjectTypeEntity> data) {
                mView.setProjectTypes(data);

            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });




    }
}
