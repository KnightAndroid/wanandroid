package com.knight.wanandroid.module_project.module_presenter;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_project.module_contract.ProjectArticleContract;
import com.knight.wanandroid.module_project.module_entity.ProjectArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/27 18:02
 * @descript:项目列表
 */
public class ProjectArticlePresenter extends ProjectArticleContract.ProjectArticleDataPresenter {
    @Override
    public void requestProjectArticle(BaseDBActivity activity, int page, int cid,boolean isNewProject) {
        final ProjectArticleContract.ProjectArticleView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestProjectArticle(activity, page, cid, isNewProject,new MvpListener<ProjectArticleListEntity>() {
            @Override
            public void onSuccess(ProjectArticleListEntity data) {
                mView.setProjectArticle(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });


    }


}
