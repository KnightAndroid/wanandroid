package com.knight.wanandroid.module_project.module_contract;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_project.module_entity.ProjectArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/27 17:17
 * @descript:
 */
public interface ProjectArticleContract {
    interface ProjectArticleView extends BaseView {
        void setProjectArticle(ProjectArticleListEntity projectArticle);

    }

    interface ProjectArticleModel extends BaseModel{
        void requestProjectArticle(BaseDBActivity activity, int page, int cid, boolean isNewProject,MvpListener mvpListener);

    }

    abstract class ProjectArticleDataPresenter extends BasePresenter<ProjectArticleModel,ProjectArticleView>{
        public abstract void requestProjectArticle(BaseDBActivity activity, int page, int cid,boolean isNewProject);

    }












}
