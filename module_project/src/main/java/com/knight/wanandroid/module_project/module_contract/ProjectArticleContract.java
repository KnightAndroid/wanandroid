package com.knight.wanandroid.module_project.module_contract;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
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
        //收藏站内文章
        void collectArticleSuccess(int position);
        //取消站内文章
        void cancelArticleSuccess(int position);

    }

    interface ProjectArticleModel extends BaseModel{
        void requestProjectArticle(BaseFragment fragment, int page, int cid, boolean isNewProject, MvpListener mvpListener);
        //收藏站内文章
        void requestCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
        //取消收藏站内文章
        void requestCancelCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
    }

    abstract class ProjectArticleDataPresenter extends BasePresenter<ProjectArticleModel,ProjectArticleView>{
        public abstract void requestProjectArticle(int page, int cid,boolean isNewProject);
        public abstract void requestCollectArticle(int collectArticleId,int position);
        public abstract void requestCancelCollectArticle(int collectArticleId,int position);
    }












}
