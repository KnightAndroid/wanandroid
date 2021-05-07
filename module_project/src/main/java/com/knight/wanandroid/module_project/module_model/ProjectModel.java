package com.knight.wanandroid.module_project.module_model;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_project.module_contract.ProjectContract;
import com.knight.wanandroid.module_project.module_entity.ProjectTypeEntity;
import com.knight.wanandroid.module_project.module_request.ProjectTitleApi;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/26 20:07
 * @descript:
 */
public class ProjectModel implements ProjectContract.ProjectModel {
    @Override
    public void requestProjectTypes(BaseFragment fragment, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new ProjectTitleApi())
                .request(new HttpCallback<HttpData<List<ProjectTypeEntity>>>(fragment){


                    @Override
                    public void onSucceed(HttpData<List<ProjectTypeEntity>> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });

    }
}
