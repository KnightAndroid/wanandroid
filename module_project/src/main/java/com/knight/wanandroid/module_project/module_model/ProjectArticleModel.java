package com.knight.wanandroid.module_project.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_project.module_contract.ProjectArticleContract;
import com.knight.wanandroid.module_project.module_entity.ProjectArticleListEntity;
import com.knight.wanandroid.module_project.module_request.ProjectArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/27 17:49
 * @descript:
 */
public class ProjectArticleModel implements ProjectArticleContract.ProjectArticleModel {
    @Override
    public void requestProjectArticle(BaseDBActivity activity, int page, int cid, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new ProjectArticleApi().setPage(page).setCid(cid))
                .request(new HttpCallback<HttpData<ProjectArticleListEntity>>(activity) {


                             @Override
                             public void onSucceed(HttpData<ProjectArticleListEntity> result) {
                                 mvpListener.onSuccess(result.getData());
                             }

                             @Override
                             public void onFail(Exception e) {
                                 mvpListener.onError(e.getMessage());
                             }
                         }


                );

    }
}
