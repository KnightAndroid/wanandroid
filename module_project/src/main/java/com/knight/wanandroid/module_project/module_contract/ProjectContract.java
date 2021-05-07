package com.knight.wanandroid.module_project.module_contract;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_project.module_entity.ProjectTypeEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/26 19:59
 * @descript:
 */
public interface ProjectContract {

    interface ProjectView extends BaseView{
        void setProjectTypes(List<ProjectTypeEntity> projectTypes);
    }

    interface ProjectModel extends BaseModel{
        void requestProjectTypes(BaseFragment fragment, MvpListener mvpListener);
    }

    abstract class ProjectDataPresenter extends BasePresenter<ProjectModel,ProjectView>{
        public abstract void requestProjectTypes();
    }





}
